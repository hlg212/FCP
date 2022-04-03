package io.github.hlg212.gateway.cache;

import io.github.hlg212.fcf.api.RouteApi;
import io.github.hlg212.fcf.cache.CacheHandlerAdapter;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.gateway.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2019-03-01 10:15
 **/
@Slf4j
@Component
public class RouteCacheHandler extends CacheHandlerAdapter {

    @Autowired
    private RouteService routeService;
    @Autowired
    private RouteApi routeApi;

    @Override
    public void onPut(String cacheName, String key, String value) {
        log.debug("put cache:{} , key:{} ,",cacheName,key);
        if( !check(key) )
        {
            routeService.save( routeApi.getById(key) );
        }
    }

    @Override
    public void onRemove(String cacheName, String key) {
        log.debug("remove cache:{} , key:{} ,",cacheName,key);
        if( !check(key) )
        {
            routeService.deleteById(key);
        }
    }

    @Override
    public void onRefresh(String cacheName, String key) {
        log.debug("refresh cache:{} , key:{} ,",cacheName,key);
        if( !check(key) )
        {
            routeService.save( routeApi.getById(key) );
        }
    }

    private boolean check(String str)
    {
        if( str.startsWith(Constants.RouteKey.getAllRoutes_prefix) )
        {
            return true;
        }
        return false;
    }

}
