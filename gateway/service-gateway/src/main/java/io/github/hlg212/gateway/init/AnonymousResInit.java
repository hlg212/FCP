package io.github.hlg212.gateway.init;

import io.github.hlg212.fcf.api.AnonymousResApi;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.gateway.annotation.SecurityConditional;
import io.github.hlg212.gateway.matcher.AntServerWebExchangeMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2018-11-26 14:37
 **/
@Component
@Slf4j
@SecurityConditional
public class AnonymousResInit implements ApplicationListener<ApplicationReadyEvent>  {
    @Autowired
    private AnonymousResApi anonymousResApi;

    public static AnonymousResServerWebExchangeMatcher matcher = new AnonymousResServerWebExchangeMatcher();
    private static boolean isinit = false;
    private static final String LOCK="ANONYMOUSRESINIT_LOCK";
   // @PostConstruct
    public void init() {

        if (isinit)
        {
            return;
        }
        synchronized (LOCK)
        {
            refresh();
            isinit = true;
        }
    }


    public void refresh()
    {
        synchronized (LOCK)
        {
            List<String> ress = anonymousResApi.getAllUrls();

            if( ress == null || ress.isEmpty() ) {
                return;
            }
            matcher.matcher = new AntServerWebExchangeMatcher(ress);
        }
    }

    @Override
    @Async
    @Retryable(value = Exception.class,maxAttempts = 20,backoff = @Backoff(value = 5000))
    public void onApplicationEvent(ApplicationReadyEvent event) {
        init();
    }

    @Recover
    public void recover(Exception e) {
        log.warn("拉取匿名资源出现失败!",e);
    }

    private static class AnonymousResServerWebExchangeMatcher implements  ServerWebExchangeMatcher
    {
        private AnonymousResServerWebExchangeMatcher()
        {

        }

        private ServerWebExchangeMatcher matcher = null;

        @Override
        public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
            if( this.matcher == null ) {
                return MatchResult.notMatch();
            }
            return this.matcher.matches(serverWebExchange);
        }
    }
}
