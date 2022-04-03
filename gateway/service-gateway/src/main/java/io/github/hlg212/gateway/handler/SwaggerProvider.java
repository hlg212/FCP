package io.github.hlg212.gateway.handler;

import io.github.hlg212.fcf.util.DiscoveryClientHelper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: swagger 网关代理控制
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

       List<String> services =  DiscoveryClientHelper.getServices();
       if( services != null )
       {
           for( String serviceName : services )
           {
               resources.add(swaggerResource(serviceName.toUpperCase(), "/" + serviceName.toLowerCase() +API_URI));
           }
       }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
