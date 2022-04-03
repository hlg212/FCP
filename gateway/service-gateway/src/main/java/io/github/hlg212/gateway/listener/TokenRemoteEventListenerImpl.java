package io.github.hlg212.gateway.listener;

import io.github.hlg212.fcf.event.TokenRemoteEvent;
import io.github.hlg212.fcf.listener.TokenRemoteEventListener;
import io.github.hlg212.gateway.cache.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@CacheConfig(cacheManager = io.github.hlg212.fcf.cache.Constants.CacheManager.SimpleCacheManager,cacheNames = Constants.OA)
public class TokenRemoteEventListenerImpl implements TokenRemoteEventListener {

    @Autowired
    @Lazy
    private TokenRemoteEventListenerImpl self;

    @Override
    @EventListener
    public void onEvent(TokenRemoteEvent event) {

        self.remote(event.getToken());
        self.remoteWap(event.getToken());
    }

    @CacheEvict(key = "#p0")
    public void remote(String token)
    {
        log.debug("local OAuth2Authentication remote:{}",token);
    }

    @CacheEvict(key = "'wap_'+#p0")
    public void remoteWap(String token)
    {
        log.debug("local OAuth2Authentication remote:{}",token);
    }
}
