package io.github.hlg212.gateway.oauth;

import io.github.hlg212.gateway.init.UrlAuthResInit;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.authorization.AuthorityReactiveAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @description: 请求权限管理器
 * 如果请求的地址被配置了权限，则要求改用户也要有相应的权限
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class UrlAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private AuthenticatedReactiveAuthorizationManager<AuthorizationContext>  authenticated =  AuthenticatedReactiveAuthorizationManager.authenticated();

    private ServerWebExchangeMatcher matcher = UrlAuthResInit.matcher;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

       // Mono.empty().publishOn(Schedulers.elastic())
        return  authenticated.check(mono,authorizationContext).filter(authorizationDecision -> authorizationDecision.isGranted())
                .flatMap(authorizationDecision -> {
                    return matcher.matches(authorizationContext.getExchange()).filter(matchResult -> matchResult.isMatch()).flatMap(matchResult -> {
                        Mono<AuthorizationDecision> ad = Mono.just(new AuthorizationDecision(true));
                        String authority = matchResult.getVariables().get("key").toString();
                        //authority = "vvvvv";
                        if (StringUtils.isNotEmpty(authority)) {
                            ad = AuthorityReactiveAuthorizationManager.hasAuthority(authority).check(mono, authorizationContext);
                        }
                        return ad;
                    }).defaultIfEmpty(new AuthorizationDecision(true));
                });

    }
}
