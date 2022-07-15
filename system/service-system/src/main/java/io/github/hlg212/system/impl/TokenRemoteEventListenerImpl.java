package io.github.hlg212.system.impl;

import io.github.hlg212.fcf.event.TokenRemoteEvent;
import io.github.hlg212.fcf.listener.TokenRemoteEventListener;
import io.github.hlg212.system.util.OAuthTokenCacheHelper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TokenRemoteEventListenerImpl implements TokenRemoteEventListener {

    @Override
    @EventListener
    public void onEvent(TokenRemoteEvent event) {
        OAuthTokenCacheHelper.remote(event.getToken());
    }


}
