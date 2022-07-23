package io.github.hlg212.task.conf;

import feign.RequestInterceptor;
import io.github.hlg212.task.xxl.interceptor.XxlAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

public class XxlAuthConfiguration {

    @Bean
    public RequestInterceptor authInterceptor()
    {
        return new XxlAuthRequestInterceptor();
    }


}
