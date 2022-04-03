package io.github.hlg212.gateway.cache;

import io.github.hlg212.fcf.cache.CacheHandlerAdapter;
import io.github.hlg212.fcf.cache.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BlackWhiteListCacheHandler extends CacheHandlerAdapter {

    @Autowired
    private BlackWhiteListCacheHandler self;


    @Override
    public void onRemove(String cacheName, String key) {
        log.debug("remove cache:{} , key:{} ,",cacheName,key);
        clear(key);
    }

    @Override
    public void onRefresh(String cacheName, String key) {
        log.debug("refresh cache:{} , key:{} ,",cacheName,key);
        clear(key);
    }

    @Override
    public void onDel(String cacheName) {
        self.clearAll();
    }

    private void clear(String key)
    {
        if( key.startsWith(Constants.BlackWhiteListKey.getBlackWhiteList_prefix) )
        {
           self.clear();
        }
       else if( key.startsWith(Constants.BlackWhiteListKey.getBlackWhiteListByAppCode_prefix) )
        {
            self.clearApp(key);
        }
       else if( key.startsWith(Constants.BlackWhiteListKey.getBlackWhiteListByAccount_prefix) )
        {
            self.clearUser(key);
        }
        else
        {
            log.warn(" cache key [{}] 未实现清理",key);
        }

    }

    @CacheEvict(value =Constants.BlackWhiteList,allEntries = true,cacheManager = Constants.CacheManager.SimpleCacheManager)
    public void clearAll()
    {
        log.debug("clear clearAll BlackWhiteList cache");
    }

    @CacheEvict(value =Constants.BlackWhiteList,key=Constants.BlackWhiteListKey.getBlackWhiteList_spel,cacheManager = Constants.CacheManager.SimpleCacheManager)
    public void clear()
    {
        log.debug("clear BlackWhiteList cache ");
    }

    @CacheEvict(value =Constants.BlackWhiteList,key="#p0",cacheManager = Constants.CacheManager.SimpleCacheManager)
    public void clearApp(String key)
    {
        log.debug("clear App [{}] BlackWhiteList cache",key);
    }

    @CacheEvict(value =Constants.BlackWhiteList,key="#p0",cacheManager = Constants.CacheManager.SimpleCacheManager)
    public void clearUser(String key)
    {
        log.debug("clear User [{}] BlackWhiteList cache",key);
    }

}
