package io.github.hlg212.gateway.matcher;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 请求 header 匹配器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class RequestHeaderRequestMatcher implements ServerWebExchangeMatcher
{
    private final String expectedHeaderName;
    private final String expectedHeaderValue;

    public RequestHeaderRequestMatcher(String expectedHeaderName) {
        this(expectedHeaderName, (String)null);
    }
    public RequestHeaderRequestMatcher(String expectedHeaderName, String expectedHeaderValue) {
        this.expectedHeaderName = expectedHeaderName;
        this.expectedHeaderValue = expectedHeaderValue;
    }
    @Override
    public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
        String actualHeaderValue = serverWebExchange.getRequest().getHeaders().getFirst(this.expectedHeaderName);
        if (this.expectedHeaderValue == null) {
            return actualHeaderValue != null ? MatchResult.match() : MatchResult.notMatch();
        } else {
            return this.expectedHeaderValue.equals(actualHeaderValue) ? MatchResult.match() : MatchResult.notMatch();
        }
    }
}