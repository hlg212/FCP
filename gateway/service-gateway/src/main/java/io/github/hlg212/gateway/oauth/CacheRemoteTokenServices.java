package io.github.hlg212.gateway.oauth;

import io.github.hlg212.gateway.cache.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Slf4j
@CacheConfig(cacheManager = io.github.hlg212.fcf.cache.Constants.CacheManager.SimpleCacheManager,cacheNames = Constants.OA)
public class CacheRemoteTokenServices extends RemoteTokenServices {
    @Override
    @Cacheable(key = "#p0")
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        try {
            return super.loadAuthentication(accessToken);
        }catch (Exception e)
        {
            log.warn("token[{}]验证失败!",accessToken,e);
            throw  e;
        }
    }

}
