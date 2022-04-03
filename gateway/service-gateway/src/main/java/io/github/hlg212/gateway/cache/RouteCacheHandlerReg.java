package io.github.hlg212.gateway.cache;

import io.github.hlg212.fcf.cache.CacheHandler;
import io.github.hlg212.fcf.cache.CacheHandlerReg;
import io.github.hlg212.fcf.cache.Constants;
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
public class RouteCacheHandlerReg implements CacheHandlerReg {

    @Autowired
    private RouteCacheHandler handler;

    @Override
    public String getCacheName() {
        return Constants.Route;
    }

    @Override
    public CacheHandler getCacheHandler() {
        return handler;
    }
}
