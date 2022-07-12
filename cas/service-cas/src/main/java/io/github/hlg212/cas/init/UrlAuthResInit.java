package io.github.hlg212.cas.init;

import io.github.hlg212.fcf.api.AuthApi;
import io.github.hlg212.fcf.model.basic.IRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 项目启动时，加载权限资源
 * @author: huangligui
 * @create: 2018-11-26 14:37
 **/
@Component
@Slf4j
@ConditionalOnProperty(matchIfMissing = false,value = "cas.filterSecurity",prefix = "htcf")
public class UrlAuthResInit  implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private AuthApi authApi;
    private Map<String,String> urlMap;

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
            if( ress == null || ress.isEmpty() ) {
                return;
            }
            Map m = new HashMap();
            for( IRes r : ress )
            {
                if( StringUtils.isNotEmpty( r.getPath() )  && StringUtils.isNotEmpty( r.getCode() ) )
                {
                    m.put(r.getPath(),r.getCode());
                }

            }
            urlMap = m;
        }
    }

    public Map<String, String> getUrlMap() {
        return urlMap;
    }


    @Recover
    public void recover(Exception e) {
        log.warn("拉取资源权限失败，请检查system服务是否正常!",e);
    }

    @Override
    @Async
    @Retryable(value = Exception.class,maxAttempts = 20,backoff = @Backoff(value = 5000))
    public void onApplicationEvent(ApplicationReadyEvent event) {
        init();
    }

}
