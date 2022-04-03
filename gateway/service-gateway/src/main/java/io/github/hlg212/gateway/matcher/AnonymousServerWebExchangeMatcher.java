package io.github.hlg212.gateway.matcher;

import io.github.hlg212.gateway.annotation.SecurityConditional;
import io.github.hlg212.gateway.init.AnonymousResInit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


/**
 * @description: 匿名过滤器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Component
@ConfigurationProperties(prefix = "fcf.gateway.security")
@SecurityConditional
public class AnonymousServerWebExchangeMatcher  implements ServerWebExchangeMatcher {

    private ServerWebExchangeMatcher anonymousMatcher = ServerWebExchangeMatchers.matchers(  AnonymousResInit.matcher);;

    public void setAnonymous(List<String> anonymous)
    {
        anonymousMatcher =  ServerWebExchangeMatchers.matchers( new AntServerWebExchangeMatcher(anonymous)
        , AnonymousResInit.matcher);
    }

    @Override
    public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
       if( HttpMethod.OPTIONS.compareTo( serverWebExchange.getRequest().getMethod() ) == 0 )
        {
            return MatchResult.match();
        }

      return anonymousMatcher.matches(serverWebExchange);

       // return anonymousMatcher.matches(serverWebExchange);
    }
}
