package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.listener.UserLoginListener;
import io.github.hlg212.fcf.model.mc.OnlineUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component(Constants.appName + ".UserLoginListener")
public class UserLoginEventListenerImpl implements UserLoginListener {


    @Autowired
    private OnlineUserService onlineUserService;


    @Override
    @EventListener
    public void onEvent(UserLoginEvent event) {
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setLoginDate(new Date(event.getTimestamp()));
        onlineUser.setUserId( event.getUserId());
        onlineUser.setId(extractKey(event));
        onlineUser.setToken(event.getToken());
        onlineUser.setClientId(event.getClientId());
        onlineUser.setUsername(event.getUserName());
        onlineUser.setAppCode(event.getAppCode());
        onlineUser.setIp(event.getIp());
        onlineUserService.save(onlineUser);
    }

    private String extractKey(UserLoginEvent event)
    {
        return event.getLoginType() + "_" + event.getToken();
    }

}
