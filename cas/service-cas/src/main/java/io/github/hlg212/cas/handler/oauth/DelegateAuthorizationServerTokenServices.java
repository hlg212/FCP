package io.github.hlg212.cas.handler.oauth;

import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.constants.FrameCommonConstants;
import io.github.hlg212.fcf.event.TokenRemoteEvent;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.EventPublisherHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Date;

@Slf4j
public class DelegateAuthorizationServerTokenServices implements AuthorizationServerTokenServices {

    private AuthorizationServerTokenServices delegate;

    @Autowired
    private UserApi userApi;

    public  DelegateAuthorizationServerTokenServices(AuthorizationServerTokenServices delegate )
    {
        this.delegate = delegate;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication oAuth2Authentication) throws AuthenticationException {
        OAuth2AccessToken existingAccessToken = getAccessToken(oAuth2Authentication);

        OAuth2AccessToken token =  delegate.createAccessToken(oAuth2Authentication);

        //第一次生成token 认为是登录
        if( existingAccessToken == null )
        {
            if( !Constants.GrantType.CLIENT_CREDENTIALS.getValue().equals(oAuth2Authentication.getOAuth2Request().getGrantType())) {
                try {
                    UserLoginEvent ule = new UserLoginEvent();
                    String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
                    IUser user = userApi.getUserByAccount(oAuth2Authentication.getName());
                    ule.setUserId(user.getId());
                    ule.setClientId(clientId);
                    ule.setToken(token.getValue());
                    ule.setUserName(oAuth2Authentication.getName());
                    ule.setLoginType(UserLoginEvent.LOGINTYPE_CLIENT);
                    ule.setAppCode(HttpServletHelper.getRequest().getParameter("appCode"));
                    String ip = HttpServletHelper.getRequest().getHeader(FrameCommonConstants.CLIENT_IP);
                    if(StringUtils.isEmpty(ip))
                    {
                        ip = HttpServletHelper.getClientIp();
                    }
                    ule.setIp(ip);

                    log.info("client:[{}],用户[{}] token生成!", clientId, user.getAccount());
                    EventPublisherHelper.publish(ule);
                } catch (Exception e) {
                    log.warn("发布UserLoginEvent异常!", e);
                }
            }
        }
        else
        {
            log.info("移除token[{}]缓存", token);
            pubTokenEvent(token.getValue());
        }
        return token;
    }

    private void pubTokenEvent(String  token)
    {
        TokenRemoteEvent event = new TokenRemoteEvent();
        event.setToken(token);
        event.setDate(new Date());
        EventPublisherHelper.publish(event);
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s, TokenRequest tokenRequest) throws AuthenticationException {
        return delegate.refreshAccessToken(s,tokenRequest);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return delegate.getAccessToken(oAuth2Authentication);
    }
}
