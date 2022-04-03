package io.github.hlg212.gateway.filter;

import io.github.hlg212.gateway.handler.SwaggerProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * @description: 代理 swagger 模式，header设置
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {
    private static final String HEADER_NAME = "X-Forwarded-Prefix";
    public static final String  X_FORWARDED_FOR = "X-Forwarded-For";
    @Override
    public GatewayFilter apply(Object config) {
            return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            //判断透传
                if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
                    return chain.filter(exchange);
                }

                String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
                ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
                ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
                return chain.filter(newExchange);

        };
    }

}
