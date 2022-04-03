package io.github.hlg212.gateway.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2018-11-16 10:41
 **/
@Component
public class NantPathRoutePredicateFactory extends AntPathRoutePredicateFactory {

    public NantPathRoutePredicateFactory() {
        super();
    }

    @Override
    public Predicate<ServerWebExchange> apply(NantPathRoutePredicateFactory.Config config) {
       final Predicate<ServerWebExchange> sp =  super.apply(config);
        return (exchange) -> {
           return !sp.test(exchange);
        };
    }

}
