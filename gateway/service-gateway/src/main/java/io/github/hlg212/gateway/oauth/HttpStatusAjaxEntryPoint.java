package io.github.hlg212.gateway.oauth;

import io.github.hlg212.gateway.exception.UserIpRangeNotLlowedException;
import io.github.hlg212.gateway.util.BlackWhiteListUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
 * @description: 未授权请求的处理
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class HttpStatusAjaxEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        String origin =  exchange.getRequest().getHeaders().getOrigin();
        if(StringUtils.isEmpty(origin))
        {
            origin = "*";
        }
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        if( e instanceof UserIpRangeNotLlowedException )
        {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return BlackWhiteListUtil.thrLimitExc(exchange);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.empty();
    }

}
