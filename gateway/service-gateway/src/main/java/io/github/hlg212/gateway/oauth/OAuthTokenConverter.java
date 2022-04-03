package io.github.hlg212.gateway.oauth;

import io.github.hlg212.gateway.util.TokenHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @description: oauth  token 转换器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class OAuthTokenConverter implements Function<ServerWebExchange, Mono<Authentication>> {
    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        String token = TokenHelper.getToken(exchange);
        if (StringUtils.isNotEmpty(token) ) {
            return Mono.just(new PreAuthenticatedAuthenticationToken(token, ""));
        }

        return Mono.empty();
    }

}