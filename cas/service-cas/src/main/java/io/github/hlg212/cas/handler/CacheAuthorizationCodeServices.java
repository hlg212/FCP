package io.github.hlg212.cas.handler;

import io.github.hlg212.cas.cache.Constants;
import io.github.hlg212.fcf.annotation.CacheRead;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.io.UnsupportedEncodingException;

@Slf4j
@CacheConfig(cacheNames = {Constants.AuthorizationCode})
public class CacheAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    @Autowired(required = false)
    @Lazy
    private CacheAuthorizationCodeServices self;

    @Override
    protected void store(String code, OAuth2Authentication oAuth2Authentication) {
        self.saveCache(code,oAuth2Authentication);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        String bs =  self.get(code);
        OAuth2Authentication oAuth2Authentication = null;
        if( StringUtils.isEmpty(bs) ) {
            return null;
        }
        try {
            oAuth2Authentication = (OAuth2Authentication) SerializationUtils.deserialize(bs.getBytes("ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            log.error("AuthorizationCode 反序列化错误!",e);
        }

        self.removeCache(code);
        return oAuth2Authentication;
    }


    @CachePut(key="#p0")
    public String saveCache(String code, OAuth2Authentication oAuth2Authentication)
    {
        byte[] bs= SerializationUtils.serialize(oAuth2Authentication);

        try {
            return new String(bs,"ISO8859-1");
        } catch (UnsupportedEncodingException e) {
           log.error("AuthorizationCode 序列化错误!",e);
        }
        return null;
    }

    @CacheRead(key="#p0")
    public String get(String code)
    {
        return null;
    }

    @CacheEvict(key="#p0")
    public void removeCache(String code)
    {

    }
}
