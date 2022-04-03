package io.github.hlg212.gateway.handler;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2018-11-16 10:41
 **/
@Component
public class AntPathRoutePredicateFactory extends AbstractRoutePredicateFactory<AntPathRoutePredicateFactory.Config> {

    public AntPathRoutePredicateFactory() {
        super(AntPathRoutePredicateFactory.Config.class);
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("pattern");
    }
    @Override
    public Predicate<ServerWebExchange> apply(AntPathRoutePredicateFactory.Config config) {

        return (exchange) -> {
            PathContainer path = PathContainer.parsePath(exchange.getRequest().getURI().getPath());
            String uri = path.value();
            if( config.patterns != null ) {
                for (int i = 0; i < config.patterns.length; i++) {
                    boolean match = config.matcher.match(config.patterns[i], uri);
                    if (match) {
                        return true;
                    }
                }
            }
                return false;
        };
    }

    public static class Config {
        private String[] patterns;
        private AntPathMatcher matcher = new AntPathMatcher();

        public Config() {
        }

        public String[] getPatterns() {
            return this.patterns;
        }

        public AntPathRoutePredicateFactory.Config setPattern(String pattern) {
            this.patterns = pattern.split("\\|");
            return this;
        }
    }

}
