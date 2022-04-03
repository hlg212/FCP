package io.github.hlg212.gateway.filter;

import io.github.hlg212.gateway.util.Constants;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.cloud.netflix.ribbon.RibbonStatsRecorder;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.SocketException;


@Component
@Slf4j
public class  RibbonStatsRecorderGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Autowired
    private SpringClientFactory springClientFactory;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> chain.filter(exchange).doOnSuccessOrError((aVoid, throwable) -> {
            log.debug("aVoid:{},throwable:{}", aVoid, throwable);
            ServiceInstance instance = exchange.getAttribute(Constants.CHOOSE_SERVICE_INSTANCE);
            if( instance != null )
            {
                Server server = null;
                if (instance instanceof RibbonLoadBalancerClient.RibbonServer) {
                    server = ((RibbonLoadBalancerClient.RibbonServer) instance).getServer();
                }
                log.debug("server:{}", server);
                RibbonLoadBalancerContext context = springClientFactory.getLoadBalancerContext(instance.getServiceId());
                RibbonStatsRecorder statsRecorder = new RibbonStatsRecorder(context, server);
                if (throwable != null) {
                    statsRecorder.recordStats(throwable);
                } else {
                    statsRecorder.recordStats(aVoid);
                }
            }
        }).onErrorMap(Exception.class,(e) -> {
            //  retry 下列子类进行重试
            //class java.io.IOException
            //class org.springframework.cloud.gateway.support.TimeoutException
            // Hystrix 会对异常进行封装，这里需要拆包
            if( SocketException.class.isInstance(e) )
            {
                return e;
            }
            else if( SocketException.class.isInstance(e.getCause()) )
            {
                return e.getCause();
            }
            return e;
        });
    }
}
