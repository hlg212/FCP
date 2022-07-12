package io.github.hlg212.cas.conf;

import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.annotation.CacheConditional;
import io.github.hlg212.fcf.core.cache.CusRedisCacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration(Constants.appName + ".CacheConfig")
@CacheConditional
public class CacheConfig {

    @Bean
    public CusRedisCacheConfiguration onlineUserCacheConfiguration(RedisSerializer redisSerializer)
    {
        CusRedisCacheConfiguration cusRedisCacheConfiguration = new CusRedisCacheConfiguration();
        cusRedisCacheConfiguration.setName(io.github.hlg212.cas.cache.Constants.OnlineUser);

        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
        cusRedisCacheConfiguration.setRedisCacheConfiguration(defaultCacheConfiguration);

        return cusRedisCacheConfiguration;
    }

}
