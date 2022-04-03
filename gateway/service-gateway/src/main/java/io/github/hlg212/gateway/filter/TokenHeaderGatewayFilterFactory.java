package io.github.hlg212.gateway.filter;

import io.github.hlg212.gateway.util.TokenHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @description: 认证token透传
 * 网关将认证信息按照 oauth 方式传递给后续服务
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Component
public class TokenHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if( !request.getHeaders().containsKey(TokenHelper.TOKEN_KEY) )
            {
                String token = TokenHelper.getToken(exchange);
                if( StringUtils.isNotEmpty(token) )
                {
                    ServerHttpRequest newRequest = request.mutate().header(TokenHelper.TOKEN_KEY,TokenHelper.BEARER + token).build();
                    ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
                    return chain.filter(newExchange);
                }
            }
            return  chain.filter(exchange);
        };
    }
}
