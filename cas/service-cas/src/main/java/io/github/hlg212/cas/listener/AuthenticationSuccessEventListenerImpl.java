package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.UserLoginLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
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
public class AuthenticationSuccessEventListenerImpl implements ApplicationListener<AuthenticationSuccessEvent>
{
    @Autowired
    private UserLoginLockService userLoginLockService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        userLoginLockService.deleteById(authenticationSuccessEvent.getAuthentication().getName());
    }
}
