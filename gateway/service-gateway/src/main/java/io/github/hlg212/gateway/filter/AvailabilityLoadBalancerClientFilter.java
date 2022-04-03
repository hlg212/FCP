package io.github.hlg212.gateway.filter;

import io.github.hlg212.gateway.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
public class AvailabilityLoadBalancerClientFilter extends LoadBalancerClientFilter {



    public AvailabilityLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            URI url = (URI)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
             String schemePrefix = (String)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
            if (url != null && ("lb".equals(url.getScheme()) || "lb".equals(schemePrefix))) {

                ServiceInstance instance = this.loadBalancer.choose(url.getHost());
                exchange.getAttributes().put(Constants.CHOOSE_SERVICE_INSTANCE, instance);
               return super.filter(exchange, chain);
            }
        return super.filter(exchange, chain);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        return (ServiceInstance)exchange.getAttribute(Constants.CHOOSE_SERVICE_INSTANCE);
    }

}
