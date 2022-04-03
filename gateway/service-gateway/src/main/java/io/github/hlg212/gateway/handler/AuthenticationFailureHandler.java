package io.github.hlg212.gateway.handler;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import reactor.core.publisher.Mono;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2018-11-07 11:16
 **/
public class AuthenticationFailureHandler extends ServerAuthenticationEntryPointFailureHandler {

    public AuthenticationFailureHandler(ServerAuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        clearAuthorization(webFilterExchange);
        return super.onAuthenticationFailure(webFilterExchange, exception);
    }

    private void clearAuthorization(WebFilterExchange webFilterExchange)
    {
        ResponseCookie c = ResponseCookie.from("Authorization","").maxAge(0).path("/").build();
        webFilterExchange.getExchange().getResponse().addCookie(c);
    }
}
