package io.github.hlg212.esb.listener;

import io.github.hlg212.esb.util.Constants;
import io.github.hlg212.fcf.event.RemoteApplicationReadyEvent;
import io.github.hlg212.fcf.listener.RemoteApplicationReadyListener;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description: <功能描述>
 * @author: huangligui
 * @create: 2019-01-23 10:29
 **/
@Component(Constants.appName + ".RemoteApplicationReadyListener")
@Slf4j
public class RemoteApplicationReadyListenerImpl implements RemoteApplicationReadyListener {
	
    @Override
    @Async
    @EventListener
    public void onEvent(RemoteApplicationReadyEvent event) {
        log.info("{}（{}:{}）服务启动!",event.getAppName(),event.getAppIpAddress(),event.getAppPort());       
    }
}
