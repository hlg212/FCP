package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.OnlineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SessionExpiredEventListenerImpl implements ApplicationListener<SessionExpiredEvent>
{
    @Autowired
    private OnlineUserService onlineUserService;
    @Override
    public void onApplicationEvent(SessionExpiredEvent event) {
        onlineUserService.expiresSession(event.getSessionId());
    }
}
