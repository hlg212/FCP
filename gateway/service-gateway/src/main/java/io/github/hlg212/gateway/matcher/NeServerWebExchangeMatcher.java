package io.github.hlg212.gateway.matcher;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 取反匹配器
 * @author: huangligui
 * @create: 2018-11-27 16:37
 **/
public class NeServerWebExchangeMatcher implements ServerWebExchangeMatcher{

    private ServerWebExchangeMatcher matcher = null;

    public NeServerWebExchangeMatcher(ServerWebExchangeMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange serverWebExchange) {
        //return MatchResult.match();
        return matcher.matches(serverWebExchange)
                .flatMap(matchResult -> {
                    if( matchResult.isMatch() )
                    {
                        return ServerWebExchangeMatcher.MatchResult.notMatch();
                    }
                    else
                    {
                        return   ServerWebExchangeMatcher.MatchResult.match();
                    }
                });
    }
}
