package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.bo.LoginPasswordErrorInfoBo;
import io.github.hlg212.cas.service.UserLoginLockService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @description: 用户认证失败事件
 *
 *
 * @author: huangligui
 * @create: 2019-01-23 10:29
 **/

@Component
@Slf4j
@ConditionalOnProperty(matchIfMissing = true,name = "htcf.cas.password-error.enable")
public class AuthenticationFailureBadCredentialsEventListenerImpl implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>
{
    @Autowired
    private UserLoginLockService userLoginLockService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        Exception exception = authenticationFailureBadCredentialsEvent.getException();

        if( exception instanceof BadCredentialsException )
        {
            if( authenticationFailureBadCredentialsEvent.getAuthentication() instanceof UsernamePasswordAuthenticationToken ) {
                LoginPasswordErrorInfoBo errorInfoBo = userLoginLockService.addLoginErrorNum(authenticationFailureBadCredentialsEvent.getAuthentication().getName());
                HttpServletHelper.getRequest().setAttribute(Constants.AUTHENTICATION_LOGINPASSWORDERRORINFO, errorInfoBo);
            }
        }

    }
}
