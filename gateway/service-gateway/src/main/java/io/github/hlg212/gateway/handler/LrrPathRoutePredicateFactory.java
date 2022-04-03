package io.github.hlg212.gateway.handler;

import io.github.hlg212.fcf.service.LongRunningResService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * @description: 判断是否进入快速失败网关
 * @author: huangligui
 * @create: 2020-4-16 10:41
 **/
@Component
@Slf4j
public class LrrPathRoutePredicateFactory extends AntPathRoutePredicateFactory{

    @Autowired
    @Lazy
    private LongRunningResService longRunningResService;

    public LrrPathRoutePredicateFactory() {
        super();
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {

        final Predicate<ServerWebExchange> sp =  super.apply(config);
       return sp.or((exchange) -> {
               PathContainer path = PathContainer.parsePath(exchange.getRequest().getURI().getPath());
               String uri = path.value();
               Integer timeout = longRunningResService.getUriTimeout(uri);
               if( timeout != null )
               {
                   log.info("执行耗时资源请求：{}",uri);
                   return true;
               }
               return false;
       });
    }

}
