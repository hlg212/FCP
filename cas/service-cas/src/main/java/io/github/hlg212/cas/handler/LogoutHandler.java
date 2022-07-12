package io.github.hlg212.cas.handler;

import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.event.UserLogoutEvent;
import io.github.hlg212.fcf.util.EventPublisherHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description:  登出，然后清空token
 * @author: huangligui
 * @create: 2018-11-20 11:36
 **/

@Slf4j
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private UserApi userApi;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String appCode = request.getParameter("appCode");

        if( authentication != null ) {
            log.info("用户[{}]登出成功!",authentication.getName());
            pubLogoutEvent(authentication.getName(), appCode);
        }
    }

    private void pubLogoutEvent(String  userName,String appCode)
    {
        UserLogoutEvent logoutEvent = new UserLogoutEvent();
        logoutEvent.setAppCode(appCode);
        logoutEvent.setUserId(userApi.getUserByAccount(userName).getId());
        logoutEvent.setType(UserLogoutEvent.TYPE_LOGOUT);
        logoutEvent.setUserName(userName);
        HttpSession session = HttpServletHelper.getRequest().getSession(false);
        if( session != null )
        {
            logoutEvent.setToken(session.getId());
        }
        EventPublisherHelper.publish(logoutEvent);
    }

}
