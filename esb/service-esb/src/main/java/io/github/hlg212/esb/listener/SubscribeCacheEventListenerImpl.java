package io.github.hlg212.esb.listener;

import io.github.hlg212.esb.service.RabbitMqService;
import io.github.hlg212.fcf.api.mq.ExchangeApi;
import io.github.hlg212.fcf.api.mq.QueueApi;
import io.github.hlg212.fcf.event.cache.SubscribeCacheEvent;
import io.github.hlg212.fcf.listener.BaseListener;
import io.github.hlg212.fcf.model.mq.IBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class SubscribeCacheEventListenerImpl implements BaseListener<SubscribeCacheEvent> {

    private final static String ESB_CACHE_PREFIX = "eventServiceBus.cache";

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private QueueApi queueApi;

    @Override
    @EventListener
    @Async
    public void onEvent(SubscribeCacheEvent event) {
        String appName = event.getAppName();
        String queueName = ESB_CACHE_PREFIX + "." + event.getServiceId();
        Collection<String> cacheNames = event.getCacheEventNames();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.warn(e.getMessage(),e);
        }
        if( cacheNames != null )
        {
            for( String cacheName : cacheNames )
            {
                log.debug("app[{}] queueName[{}] subscribe cache[{}] ",appName,queueName,cacheName);
                rabbitMqService.bindExchangeToQueue(ESB_CACHE_PREFIX,queueName,"#."+cacheName);
            }
        }
        List<String> delBinds = new ArrayList();
        Collection<IBinding> iBindings = queueApi.getBinds(queueName,"esb");
        for( IBinding iBinding : iBindings )
        {
            if( iBinding.getDestination().equals(queueName) ) {
                String routeKey = iBinding.getRoutingKey().substring(2);

                if( !cacheNames.contains(routeKey) )
                {
                    delBinds.add(iBinding.getRoutingKey());
                }
            }
        }
        delBinds.remove("#."+event.getAppName()+".#");

        for( String routingKey : delBinds )
        {
            log.debug("app[{}] queueName[{}] unsubscribe cache[{}] ",appName,queueName,routingKey);
            rabbitMqService.unbindExchangeToQueue(ESB_CACHE_PREFIX,queueName,routingKey);
        }
    }
}
