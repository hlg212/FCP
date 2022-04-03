package io.github.hlg212.gateway.matcher;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

/**
 * @description: 权限验证对比器
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
public class AuthorityServerWebExchangeMatcher implements ServerWebExchangeMatcher {

    private ServerWebExchangeMatcher matcher = null;
   // private List<String> authoritys = null;
    private String authority;

    public AuthorityServerWebExchangeMatcher(String path,List<String> authoritys)
    {
        this.matcher = ServerWebExchangeMatchers.pathMatchers(path);
      // this.authoritys = authoritys;
    }
    public AuthorityServerWebExchangeMatcher(String path,String authority)
    {
        this.matcher = ServerWebExchangeMatchers.pathMatchers(path);
        // this.authoritys = authoritys;
        this.authority = authority;
    }
    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        return matcher.matches(exchange).filter(MatchResult::isMatch).map((r) -> {
            //r.getVariables().put("key",authoritys);
            r.getVariables().put("key",authority);
            return r;
        });
       // return matcher.matches(serverWebExchange);
    }
}
