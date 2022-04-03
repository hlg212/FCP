package io.github.hlg212.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@Slf4j
public class RetryExceptionTransformGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> chain.filter(exchange).onErrorMap(Exception.class,(e) -> {
            //  retry 下列子类进行重试
            //class java.io.IOException
            //class org.springframework.cloud.gateway.support.TimeoutException
            // Hystrix 会对异常进行封装，这里需要拆包
            if( e instanceof ResponseStatusException)
            {
                ResponseStatusException exception = (ResponseStatusException)e;
                if(HttpStatus.GATEWAY_TIMEOUT.equals(exception.getStatus()))
                {
                   return exception.getCause();
                }
            }
            return e;
        });
    }
}
