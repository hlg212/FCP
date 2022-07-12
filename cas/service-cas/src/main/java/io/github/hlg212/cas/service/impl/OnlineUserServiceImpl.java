package io.github.hlg212.cas.service.impl;

import io.github.hlg212.cas.cache.Constants;
import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.fcf.annotation.CacheRead;
import io.github.hlg212.fcf.annotation.CacheRemove;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.api.common.CacheApi;
import io.github.hlg212.fcf.event.TokenRemoteEvent;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.event.UserLogoutEvent;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.basic.CacheDetail;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.model.mc.IOnlineUser;
import io.github.hlg212.fcf.model.mc.OnlineUser;
import io.github.hlg212.fcf.util.AppContextHelper;
import io.github.hlg212.fcf.util.EventPublisherHelper;
import io.github.hlg212.fcf.util.FastJsonHelper;
import io.github.hlg212.fcf.util.FeignClientContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@CacheConfig(cacheNames = Constants.OnlineUser)
public class OnlineUserServiceImpl implements OnlineUserService<OnlineUser> {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserApi userApi;

    @Autowired
    @Lazy
    private OnlineUserService self;

    @Override
    @CachePut(key = "#p0.id")
    public OnlineUser save(OnlineUser onlineUserBo) {
        return onlineUserBo;
    }


    @Override
    @CacheRemove(key = "#p0")
    public void deleteById(Object... id) {
        log.debug(" deleteById:{}",id);
    }

    @Override
    @CacheRead(key = "#p0")
    public OnlineUser getById(Object id) {
        return null;
    }

    @Override
    public PageInfo findPage(String username, int pageNum, int pageSize) {
        CacheApi cacheApi = FeignClientContextUtils.getBean(CacheApi.class, AppContextHelper.getAppCode());
        PageInfo pageInfo = new PageInfo();
        PageInfo<CacheDetail> cacheDetailPageInfo =  cacheApi.getCacheDetailByCacheName(Constants.OnlineUser,username,pageNum,pageSize);
        List result = new ArrayList();
        if( cacheDetailPageInfo.getList() != null )
        {
            cacheDetailPageInfo.getList().stream().forEach(cacheDetail -> {
                result.add(FastJsonHelper.parseObject(cacheDetail.getValue()));
            });
        }
        pageInfo.setList(result);
        pageInfo.setPages(cacheDetailPageInfo.getPages());
        pageInfo.setPageNum(cacheDetailPageInfo.getPageNum());
        pageInfo.setPageSize(cacheDetailPageInfo.getPageSize());
        pageInfo.setTotal(cacheDetailPageInfo.getTotal());
        return  pageInfo;

    }



    private void killUser(String username,String sessionId) {
       Collection<SessionInformation> colls = sessionRegistry.getAllSessions(username,false);
        if( colls != null )
        {
            for( SessionInformation sessionInformation : colls )
            {
                if (sessionId.equals(sessionInformation.getSessionId())) {
                    sessionInformation.expireNow();
                    IUser user = userApi.getUserByAccount(username);
                    pubLogoutEvent(user.getId(), null, sessionInformation.getSessionId(), username, UserLogoutEvent.TYPE_KILL);
                    break;
                }
            }
        }

    }


    private void killToken(String stoken) {
        OAuth2AccessToken token = tokenStore.readAccessToken(stoken);
        Authentication authentication = null;
        if( token != null )
        {
            authentication = tokenStore.readAuthentication(stoken);
            tokenStore.removeAccessToken(token);
        }


        if( authentication != null )
        {
            IUser user = userApi.getUserByAccount(authentication.getName());
            String clientId = ((OAuth2Authentication) authentication).getOAuth2Request().getClientId();
            if( user != null )
            {
                pubLogoutEvent(user.getId(),clientId,stoken,authentication.getName(),UserLogoutEvent.TYPE_KILL);
            }

        }
        else
        {
            IOnlineUser ou = self.getById(UserLoginEvent.LOGINTYPE_CLIENT + "_" + stoken);
            if( ou != null )
            {
                pubLogoutEvent(ou.getUserId(),ou.getClientId(),stoken,ou.getUsername(),UserLogoutEvent.TYPE_KILL);
            }
        }
        pubTokenEvent(stoken, null);
    }


    @Override
    public void remoteToken(String stoken) {
        OAuth2AccessToken token = tokenStore.readAccessToken(stoken);

        if( token != null )
        {
            tokenStore.removeAccessToken(token);
        }
        pubTokenEvent(stoken, null);
    }

    @Override
    public void kill(String id) {
        IOnlineUser ou = self.getById(id);
        String idd[] = id.split("_");
        if( !idd[0].equals(UserLoginEvent.LOGINTYPE_CLIENT) )
        {
           if( ou != null )
           {
               killUser(ou.getUsername(), ou.getToken());
           }
           self.deleteById(id);

        }
        else
        {
            killToken(idd[1]);
        }
    }

    @Override
    public void expiresToken(String token) {
        log.info("token过期:{}",token);
        IOnlineUser ou = self.getById(UserLoginEvent.LOGINTYPE_CLIENT + "_" + token);
        if (ou != null){
            log.info("过期用户:{},token:{}",ou.getUsername(),token);
            pubLogoutEvent(ou.getUserId(),ou.getClientId(),ou.getToken(),ou.getUsername(),UserLogoutEvent.TYPE_INVALID);
        }
        remoteToken(token);
    }

    @Override
    public void expiresSession(String session) {
        log.info("session过期:{}",session);
        IOnlineUser ou = self.getById(UserLoginEvent.LOGINTYPE_INTERACTIVE + "_" + session);
        if (ou != null){
            log.info("过期用户:{},session:{}",ou.getUsername(),session);
            pubLogoutEvent(ou.getUserId(),ou.getClientId(),ou.getToken(),ou.getUsername(),UserLogoutEvent.TYPE_INVALID);

        }

    }

    @Override
    public boolean isInvalid(String id) {
        boolean isInvalid = true;
        IOnlineUser onlineUser = self.getById(id);
        if( onlineUser != null )
        {
            String stoken = onlineUser.getToken();
            if( StringUtils.isNotEmpty( onlineUser.getClientId() ) )
            {
                OAuth2AccessToken token = tokenStore.readAccessToken(stoken);
                if( token != null && !token.isExpired() )
                {
                    isInvalid = false;
                }
            }
            else
            {
                SessionInformation information = sessionRegistry.getSessionInformation(stoken);
                if( information != null && !information.isExpired() )
                {
                    isInvalid = false;
                }
            }
        }
        return isInvalid;
    }

    private void pubLogoutEvent(String  userId,String clientId,String token,String username,String type)
    {
        UserLogoutEvent logoutEvent = new UserLogoutEvent();
        logoutEvent.setUserId(userId);
        logoutEvent.setType(type);
        logoutEvent.setClientId(clientId);
        logoutEvent.setToken(token);
        logoutEvent.setUserName(username);
        log.info("用户[{}],token[{}] 被强制踢出!",username,token);
        EventPublisherHelper.publish(logoutEvent);
    }

    private void pubTokenEvent(String  token,String appCode)
    {
        TokenRemoteEvent event = new TokenRemoteEvent();
        event.setToken(token);
        event.setDate(new Date());
        event.setAppCode(appCode);
        EventPublisherHelper.publish(event);
    }

}
