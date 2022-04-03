package io.github.hlg212.gateway.oauth;

import io.github.hlg212.fcf.api.AppApi;
import io.github.hlg212.fcf.model.basic.App;
import io.github.hlg212.fcf.model.basic.IApp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @description: 统一登录处理器
 * 默认情况下跳转到 cas 的登录页面</br>
 * 如果请求的地址配置了自定义的系统登录地址，则跳转到自定义的登录页面
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Slf4j
public class SsoServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    @Autowired
    private AppApi appApi;

    public void setRequestCache(ServerRequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache cannot be null");
        this.requestCache = requestCache;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        URI location =  createRedirectUri(exchange);
        return this.requestCache.saveRequest(exchange).then(this.redirectStrategy.sendRedirect(exchange, location));
    }

    public void setRedirectStrategy(ServerRedirectStrategy redirectStrategy) {
        Assert.notNull(redirectStrategy, "redirectStrategy cannot be null");
        this.redirectStrategy = redirectStrategy;
    }

    private  URI createRedirectUri(ServerWebExchange exchange)
    {
        URI uri = null;
        String contextPath = getContextPath(exchange.getRequest().getPath().value());
        IApp as = null;
        try
        {
            as = getApp(contextPath);
        }
        catch (Exception e)
        {
            log.warn("找不到应用{}信息，使用认证中心登录",contextPath );
        }
        URI location = exchange.getRequest().getURI().resolve("/ssoRedirect");
        if( as != null && StringUtils.isNotEmpty(as.getLoginPage()))
        {
            String loginPage = as.getLoginPage();
            uri =  URI.create(loginPage  + "?redirect_uri="+ location.toString());
        }
        else
        {
            uri =  URI.create("/sso/ssoLogin?redirect_uri="+ location.toString());
        }
        return uri;
    }
    private static String getContextPath(String path)
    {
        String context = path;
        if( context.startsWith("/") )
        {
            context = context.substring(1);
        }
        int index = context.indexOf("/");
        if( index > 0 ) {
            context = context.substring(0, index);
        }

        return context;
    }

    private IApp getApp(String contextPath)
    {
        if( contextPath.startsWith("/") )
        {
            contextPath = contextPath.substring(1);
        }
        return appApi.getAppByCode(contextPath);
    }
}