package io.github.hlg212.cas.listener;

import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.event.UserLogoutEvent;
import io.github.hlg212.fcf.listener.UserLogoutListener;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.RtpHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component(Constants.appName + ".UserLogoutListener")
@Slf4j
public class UserLogoutEventListenerImpl implements UserLogoutListener {


    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private  UserApi  userApi;

    @Override
    @EventListener
    public void onEvent(UserLogoutEvent event) {
        String id = extractKey(event);
        log.info("清除用户在线用户:[{}],token:[{}]",event.getUserName(),event.getToken());
        onlineUserService.deleteById(id);
        IUser u = userApi.getById(event.getUserId());
        if( u != null )
        {
            RtpHelper.sendToUser(u.getAccount(), RtpHelper.MessageBuild.create("UserLogoutEvent",event));
        }
    }

    private String extractKey(UserLogoutEvent event)
    {
        if( StringUtils.isNotEmpty( event.getClientId() ) )
        {
            return UserLoginEvent.LOGINTYPE_CLIENT + "_" + event.getToken();
        }

        return UserLoginEvent.LOGINTYPE_INTERACTIVE + "_" + event.getToken();

    }

}
