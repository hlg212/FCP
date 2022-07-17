package io.github.hlg212.esb.listener;

import io.github.hlg212.esb.util.Constants;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.listener.UserLoginListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-01-23 10:29
 **/
@Component(Constants.appName + ".UserLoginListener")
@Slf4j
public class UserLoginEventListenerImpl implements UserLoginListener {

    @Override
    @Async
    @EventListener
    public void onEvent(UserLoginEvent event) {
        log.error("xx 登录了");
        
    }
}
