package io.github.hlg212.gateway.init;

import io.github.hlg212.fcf.api.AuthApi;
import io.github.hlg212.fcf.model.basic.IRes;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.gateway.annotation.SecurityConditional;
import io.github.hlg212.gateway.matcher.AuthorityServerWebExchangeMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
public class UrlAuthResInit  implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private AuthApi authApi;

    public static UrlAuthResServerWebExchangeMatcher matcher = new UrlAuthResServerWebExchangeMatcher();
    private static boolean isinit = false;
    private static final String LOCK="URLAUTHRESINIT_LOCK";
   // @PostConstruct
    private void init() {

        if (isinit)
        {
            return;
        }
        refresh();
        isinit = true;
    }

    public void refresh()
    {
        synchronized (LOCK)
        {
            List<IRes> ress = authApi.getAllAuthRes();
            if( ress == null ||  ress.isEmpty() ) {
                return;
            }
            final List<AuthorityServerWebExchangeMatcher> authorityServerWebExchangeMatchers = new ArrayList<AuthorityServerWebExchangeMatcher>();
            ress.forEach(res -> {
                if( StringUtils.isNotEmpty(res.getPath()) && StringUtils.isNotEmpty(res.getCode()) )
                //if( StringUtils.isNotEmpty(res.getPath())  )
                {
                    authorityServerWebExchangeMatchers.add(new AuthorityServerWebExchangeMatcher(res.getPath(),res.getCode()) );
                }
            });
            matcher.matcher = ServerWebExchangeMatchers.matchers(authorityServerWebExchangeMatchers.toArray(new ServerWebExchangeMatcher[0]));

        }
    }


    @Recover
    public void recover(Exception e) {
        log.warn("拉取资源权限失败，请检查basic服务是否正常!",e);
    }

    @Override
    @Async
    @Retryable(value = Exception.class,maxAttempts = 20,backoff = @Backoff(value = 5000))
    public void onApplicationEvent(ApplicationReadyEvent event) {
        init();
    }

    private static class UrlAuthResServerWebExchangeMatcher implements  ServerWebExchangeMatcher
    {
        private UrlAuthResServerWebExchangeMatcher()
        {

        }

        private ServerWebExchangeMatcher matcher = null;

        @Override
        public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
            if( matcher == null ) {
                return MatchResult.notMatch();
            }
            return matcher.matches(serverWebExchange);
        }
    }
}
