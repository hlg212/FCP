package io.github.hlg212.gateway.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
//@Slf4j
public class RefreshRoutesEventListenerImpl implements ApplicationListener<RefreshRoutesEvent> {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;
    @Override
    public void onApplicationEvent(RefreshRoutesEvent event) {
        System.out.println("RefreshRoutesEventListenerImpl:" + event.getTimestamp());
        routeDefinitionLocator.getRouteDefinitions().subscribe(routeDefinition -> {
            List<FilterDefinition> list = routeDefinition.getFilters();
            System.out.println(routeDefinition.getId() + ";size:" + list.size());
            String id = routeDefinition.getId();
            if( !id.startsWith("CompositeDiscoveryClient") )
            {
               // list.contains()

            }
            FilterDefinition fd = new FilterDefinition();
            fd.setName("Hystrix");
            Map<String,String> arg = new HashMap<>();
            arg.put("name",routeDefinition.getId() + "_globalcmd");
            arg.put("fallbackUri","forward:/errorFallback");
            fd.setArgs(arg);
            list.add(fd);
        });
    }
}
