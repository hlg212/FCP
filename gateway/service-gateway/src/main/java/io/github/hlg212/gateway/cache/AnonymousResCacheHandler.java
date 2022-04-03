package io.github.hlg212.gateway.cache;

import io.github.hlg212.fcf.cache.CacheHandlerAdapter;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.gateway.annotation.SecurityConditional;
import io.github.hlg212.gateway.init.AnonymousResInit;
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
public class AnonymousResCacheHandler extends CacheHandlerAdapter {

    @Autowired
    private AnonymousResInit anonymousResInit;


    @Override
    public void onRemove(String cacheName, String key) {
        if( check(key) )
        {
            anonymousResInit.refresh();
        }
    }

    @Override
    public void onRefresh(String cacheName, String key) {
        log.debug("refresh cache:{} , key:{} ,",cacheName,key);
        if( check(key) )
        {
            anonymousResInit.refresh();
        }
    }

    private boolean check(String str)
    {
        if( str.startsWith(Constants.AnonymousResKey.getAllUrls_prefix) )
        {
            return true;
        }
        return false;
    }

}
