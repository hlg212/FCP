package io.github.hlg212.gateway.filter;

import io.github.hlg212.gateway.annotation.SecurityConditional;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @description: 登录跳转回调过滤器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Component
@SecurityConditional
public class SsoRedirectFilter implements WebFilter {
    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();
    private ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.pathMatchers("/ssoRedirect");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return this.requiresAuthenticationMatcher.matches(exchange).filter((matchResult) -> {
            return matchResult.isMatch();
        }).switchIfEmpty(chain.filter(exchange).then(Mono.empty())).flatMap((matchResult) -> {
            return requestCache.getRedirectUri(exchange);
            //return redirectStrategy.sendRedirect(exchange,requestCache.getRedirectUri(exchange).block());
        }).flatMap(uri -> {
            return redirectStrategy.sendRedirect(exchange,uri);
        });
    }
}