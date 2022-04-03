package io.github.hlg212.gateway.init;

import io.github.hlg212.fcf.api.RouteApi;
import io.github.hlg212.fcf.model.ga.IRoute;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.gateway.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2018-11-26 14:37
 **/
@Component
@Slf4j
public class RouteInit implements ApplicationListener<ApplicationReadyEvent>  {
    @Autowired
    private RouteApi routeApi;
    private static boolean isinit = false;
    private static final String LOCK="ROUTEINIT_LOCK";
    @Autowired
    private RouteService routeService;
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
            List<IRoute>  routes = routeApi.getAllRoutes();
            if( routes == null || routes.isEmpty() ) {
                return;
            }
          for( IRoute r : routes )
          {
              routeService.save(r);
          }
        }
    }

    @Recover
    public void recover(Exception e) {
        log.warn("拉取路由失败!",e);
    }

    @Override
    @Async
    @Retryable(value = Exception.class,maxAttempts = 20,backoff = @Backoff(value = 5000))
    public void onApplicationEvent(ApplicationReadyEvent event) {
        init();
    }

}
