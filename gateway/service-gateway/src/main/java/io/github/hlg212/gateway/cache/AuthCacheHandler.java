package io.github.hlg212.gateway.cache;

import io.github.hlg212.fcf.api.RouteApi;
import io.github.hlg212.fcf.cache.CacheHandlerAdapter;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.gateway.annotation.SecurityConditional;
import io.github.hlg212.gateway.init.UrlAuthResInit;
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
@SecurityConditional
public class AuthCacheHandler extends CacheHandlerAdapter {

    @Autowired
    private UrlAuthResInit urlAuthResInit;

    @Override
    public void onDel(String cacheName) {
        urlAuthResInit.refresh();
    }
}
