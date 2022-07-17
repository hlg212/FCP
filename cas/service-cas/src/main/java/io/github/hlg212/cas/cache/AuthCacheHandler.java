package io.github.hlg212.cas.cache;

import io.github.hlg212.cas.init.UrlAuthResInit;
import io.github.hlg212.cas.properties.CasProperties;
import io.github.hlg212.fcf.cache.CacheHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@ConditionalOnProperty(value = "filterSecurity",prefix = CasProperties.PREFIX)
public class AuthCacheHandler extends CacheHandlerAdapter {

    @Autowired
    private UrlAuthResInit urlAuthResInit;

    @Override
    public void onDel(String cacheName) {
        urlAuthResInit.refresh();
    }
}
