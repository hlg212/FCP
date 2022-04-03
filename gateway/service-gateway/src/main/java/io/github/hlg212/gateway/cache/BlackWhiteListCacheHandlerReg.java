package io.github.hlg212.gateway.cache;

import io.github.hlg212.fcf.cache.CacheHandler;
import io.github.hlg212.fcf.cache.CacheHandlerReg;
import io.github.hlg212.fcf.cache.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BlackWhiteListCacheHandlerReg implements CacheHandlerReg {

    @Autowired
    private BlackWhiteListCacheHandler handler;

    @Override
    public String getCacheName() {
        return Constants.BlackWhiteList;
    }

    @Override
    public CacheHandler getCacheHandler() {
        return handler;
    }
}
