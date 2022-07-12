package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.EventPublisherHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @description: 用户登认证成功之后发出事件
 *
 * 用户直接在页面上输入账号密码
 * @author: huangligui
 * @create: 2019-01-23 10:29
 **/
@Component
@Slf4j
public class InteractiveAuthenticationSuccessEventListenerImpl implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private UserApi userApi;
    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {

        Authentication authentication = event.getAuthentication();
        UserLoginEvent ule = new UserLoginEvent();
        IUser user = userApi.getUserByAccount(authentication.getName());
        ule.setUserId(user.getId());
        ule.setLoginType(UserLoginEvent.LOGINTYPE_INTERACTIVE);
        ule.setToken( getSessionId(authentication) );
        ule.setUserName(authentication.getName());
        ule.setIp(HttpServletHelper.getClientIp());
        log.info("用户[{}]认证成功 session:[{}]!",user.getAccount(),ule.getToken());
        EventPublisherHelper.publish(ule);
    }

    private String getSessionId(Authentication authentication)
    {
        String sessionId = null;
        String oldSessionId = null;
        if( authentication.getDetails() instanceof WebAuthenticationDetails )
        {
            WebAuthenticationDetails wad = (WebAuthenticationDetails)authentication.getDetails();
            oldSessionId = wad.getSessionId();
        }

        HttpSession session = HttpServletHelper.getRequest().getSession(false);
        if( session != null )
        {
            sessionId = session.getId();
        }
        if( StringUtils.isEmpty(sessionId) )
        {
            sessionId = oldSessionId;
        }
        if( !StringUtils.equals(sessionId,oldSessionId) )
        {
            log.info("用户重新登录[{}]更改了session:[{}] -> [{}]!",authentication.getName(),oldSessionId,sessionId);
            onlineUserService.expiresSession(oldSessionId);
        }

        return sessionId;
    }

}
