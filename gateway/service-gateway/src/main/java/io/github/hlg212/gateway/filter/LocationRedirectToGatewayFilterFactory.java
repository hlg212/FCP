package io.github.hlg212.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory;

public class LocationRedirectToGatewayFilterFactory extends RedirectToGatewayFilterFactory {


    @Override
    public GatewayFilter apply(Config config) {
        return super.apply(config);


    }
}
