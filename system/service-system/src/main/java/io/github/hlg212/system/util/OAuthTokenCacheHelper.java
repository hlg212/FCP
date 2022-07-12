package io.github.hlg212.system.util;

import io.github.hlg212.fcf.api.OAuthApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.fcf.model.oauth.OAuthResult;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.OAuthApiHelper;
import io.github.hlg212.fcf.util.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@CacheConfig(cacheNames = Constants.Token,cacheManager = Constants.CacheManager.SimpleCacheManager)
@Component
public class OAuthTokenCacheHelper {

    private static OAuthTokenCacheHelper _instance;

    private static OAuthTokenCacheHelper getInstance()
    {
        if( _instance == null )
        {
            _instance = SpringHelper.getBean(OAuthTokenCacheHelper.class);
        }
        return _instance;
    }

    public static void cached(String token, OAuthResult result)
    {
        getInstance()._cached(token,result);
    }

    public static OAuthResult get(String token)
    {
       return getInstance()._get(token);
    }

    public static void remote(String token)
    {
      getInstance()._remote(token);
    }

    public static OAuthResult checkToken(String token)
    {
        return getInstance()._checkToken(token);
    }



    @CachePut(key = "#p0")
    public OAuthResult _cached(String token, OAuthResult result)
    {
        log.debug("缓存token:{}",token);
        return  result;
    }

    @CacheEvict(key = "#p0")
    public void _remote(String token)
    {
        log.debug("移除token:{}",token);
    }

    @Cacheable(key = "#p0")
    public OAuthResult _get(String token)
    {
       return null;
    }

    public OAuthResult _checkToken(String token)
    {
        OAuthResult result = get(token);
        if( result == null )
        {
            result = OAuthApiHelper.checkToken(token);
            if ( result != null && StringUtils.isNotEmpty(result.getError())) {
                ExceptionHelper.throwBusinessException(result.getErrorDescription());
            }
            cached(token,result);
        }
        return result;
    }

}
