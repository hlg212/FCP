package io.github.hlg212.gateway.conf;
//
//import io.github.hlg212.gateway.filter.AvailabilityLoadBalancerClientFilter;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.client.reactive.ReactorResourceFactory;

@Configuration
public class GatewayConfig implements Ordered {


//    @Bean
//    public AvailabilityLoadBalancerClientFilter availabilityLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties)
//    {
//        return new AvailabilityLoadBalancerClientFilter(loadBalancer,properties);
//    }

    @Bean("gateway.reactorServerResourceFactory")
    @Primary
    public ReactorResourceFactory reactorServerResourceFactory() {
        ReactorResourceFactory rrf =  new ReactorResourceFactory();
        rrf.setUseGlobalResources(false);
        return rrf;

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
