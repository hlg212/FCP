package io.github.hlg212.gateway.handler;

import io.github.hlg212.fcf.model.ActionResult;
import io.github.hlg212.fcf.model.ActionResultBuilder;
import io.github.hlg212.fcf.util.StackTraceHelper;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 断路器请求降级控制
 * 返回友好的错误提示信息
 *
 * @author: huangligui
 * @create: 2018-7-26 10:30
 **/
@RestController
@Slf4j
public class HystrixFallbackHandler {

    @RequestMapping(value = "/errorFallback")
    public Mono<ActionResult> fallback(ServerWebExchange exchange) {
        Integer code = null;
        Exception e = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        if( e instanceof ResponseStatusException)
        {
            ResponseStatusException exception = (ResponseStatusException)e;
            code = exception.getStatus().value();
        }
        ActionResultBuilder<String> result = ActionResultBuilder.serverError();
        result.withMsg(e.getMessage());
        result.withData(StackTraceHelper.getSignificantStackTrace(e));
        if( e instanceof HystrixTimeoutException)
        {
            if(StringUtils.isEmpty(e.getMessage()))
            {
                result.withMsg("Gateway Timeout");
            }
        }
        if( code != null )
        {
            result.withCode(code+"");
        }
        PathContainer path = PathContainer.parsePath(exchange.getRequest().getURI().getPath());
        String uri = path.value();
        log.warn("请求失败或超过最大超时限制:{}",uri);
        return Mono.just(result.build());
    }

}
