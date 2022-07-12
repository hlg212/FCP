package io.github.hlg212.cas.handler;

import io.github.hlg212.cas.init.UrlAuthResInit;
import io.github.hlg212.cas.properties.CasProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Map;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-03-15 13:52
 **/
@Component
@ConditionalOnProperty(value = "cas.filterSecurity",prefix = "htcf")
public class ResFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource  superMetadataSource;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private UrlAuthResInit urlAuthResInit;


    public void setSuperMetadataSource(FilterInvocationSecurityMetadataSource superMetadataSource) {
        this.superMetadataSource = superMetadataSource;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) o;
        String url = fi.getRequestUrl();


        if( casProperties.getAnonymous() != null )
        {
            for( String a : casProperties.getAnonymous() )
            {
                if(antPathMatcher.match(a,url)){
                    return superMetadataSource.getAttributes(o);
                }
            }
        }
        Map<String,String> urlMap = urlAuthResInit.getUrlMap();
        if( urlMap != null )
        {
            for(Map.Entry<String,String> entry:urlMap.entrySet()){
                if(antPathMatcher.match(entry.getKey(),url)){
                    return SecurityConfig.createList(entry.getValue());
                }
            }
        }

        //  返回代码定义的默认配置
        return superMetadataSource.getAttributes(o);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
