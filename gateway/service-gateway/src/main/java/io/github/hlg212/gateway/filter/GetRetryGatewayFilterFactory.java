package io.github.hlg212.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @description: 接口重试设置
 * 重写重试，只对GET方式的请求进行重试，如果想开启所有重试，请修改配置
 *
 *
 * @author: huangligui
 * @create: 2020-3-20 10:30
 **/

@Slf4j
@Component
public class GetRetryGatewayFilterFactory extends RetryGatewayFilterFactory {


    @Override
    public boolean exceedsMaxIterations(ServerWebExchange exchange, RetryConfig retryConfig) {
        HttpMethod httpMethod = exchange.getRequest().getMethod();
        boolean retryableMethod = retryConfig.getMethods().contains(httpMethod);
        if( !retryableMethod ) {
            return true;
        }
        return  super.exceedsMaxIterations(exchange, retryConfig);
    }
}
