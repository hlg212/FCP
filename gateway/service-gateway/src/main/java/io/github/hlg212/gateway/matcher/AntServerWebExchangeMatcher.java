package io.github.hlg212.gateway.matcher;

import org.springframework.http.server.PathContainer;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * @description: 支持ant模式的匹配器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class AntServerWebExchangeMatcher implements ServerWebExchangeMatcher {

    private List<String> patterns;

    private AntPathMatcher matcher = new AntPathMatcher();

    public AntServerWebExchangeMatcher(List<String> patterns)
    {
        this.patterns = patterns;
    }

    @Override
    public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
        PathContainer path = PathContainer.parsePath(serverWebExchange.getRequest().getURI().getPath());
        if( patterns == null || patterns.isEmpty())
        {
            return MatchResult.notMatch();
        }
        return Flux.fromStream(patterns.stream()).filter(pattern ->{
                    return matcher.match(pattern,path.value());
                }).flatMap(pattern ->{
                    return MatchResult.match();
        }).next().switchIfEmpty(MatchResult.notMatch());

       // return anonymousMatcher.matches(serverWebExchange);
    }
}
