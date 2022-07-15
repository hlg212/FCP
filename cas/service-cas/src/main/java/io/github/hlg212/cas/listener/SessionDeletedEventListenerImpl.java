package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.OnlineUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SessionDeletedEventListenerImpl implements ApplicationListener<SessionDeletedEvent>
{
    @Autowired
    private OnlineUserService onlineUserService;
    @Override
    public void onApplicationEvent(SessionDeletedEvent event) {
        onlineUserService.expiresSession(event.getSessionId());
    }
}
