package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.event.TokenRemoteEvent;
import io.github.hlg212.fcf.listener.TokenRemoteEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component(Constants.appName + ".TokenRemoteEventListener")
@Slf4j
public class TokenRemoteEventListenerImpl implements TokenRemoteEventListener {

    @Autowired
    @Lazy
    private TokenRemoteEventListenerImpl self;


    @Override
    @EventListener
    public void onEvent(TokenRemoteEvent event) {
        self.userInfoCacheRemote(event.getToken());
    }

    @CacheEvict(cacheNames = io.github.hlg212.fcf.cache.Constants.UserInfo,key = "#p0")
    public void  userInfoCacheRemote(String token)
    {
        log.info("cache {} remote token:{}",token);
    }

}
