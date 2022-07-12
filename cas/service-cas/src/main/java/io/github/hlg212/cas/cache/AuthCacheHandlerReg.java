package io.github.hlg212.cas.cache;

import io.github.hlg212.fcf.cache.CacheHandler;
import io.github.hlg212.fcf.cache.CacheHandlerReg;
import io.github.hlg212.fcf.cache.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@ConditionalOnProperty(value = "cas.filterSecurity",prefix = "htcf")
public class AuthCacheHandlerReg implements CacheHandlerReg {

    @Autowired
    private AuthCacheHandler handler;

    @Override
    public String getCacheName() {
        return Constants.Auth;
    }

    @Override
    public CacheHandler getCacheHandler() {
        return handler;
    }
}
