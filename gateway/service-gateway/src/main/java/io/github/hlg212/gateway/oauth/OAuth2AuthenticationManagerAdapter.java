package io.github.hlg212.gateway.oauth;

import io.github.hlg212.fcf.util.SpringHelper;
import io.github.hlg212.gateway.conf.OAuthSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @description: oauth 认证管理器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Slf4j
public class OAuth2AuthenticationManagerAdapter implements ReactiveAuthenticationManager {
    private AuthenticationManager authenticationManager;


    public OAuth2AuthenticationManagerAdapter(ResourceServerTokenServices tokenServices)
    {
        this.authenticationManager = SpringHelper.getBean(OAuthSecurityConfig.class).oauthManager(tokenServices);
    }

    @Override
    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.just(token).publishOn(Schedulers.elastic()).flatMap(t -> {
            try {
                return Mono.just(this.authenticationManager.authenticate(t));
            } catch (Exception x) {
                return Mono.error(new BadCredentialsException("Invalid or expired access token presented"));
            }
        }).filter(Authentication::isAuthenticated);
    }

}