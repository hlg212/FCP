package io.github.hlg212.cas.api;

import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.fcf.api.LogoutApi;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.event.UserLoginEvent;
import io.github.hlg212.fcf.event.UserLogoutEvent;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.model.mc.IOnlineUser;
import io.github.hlg212.fcf.util.EventPublisherHelper;
import io.github.hlg212.fcf.web.util.TokenHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  登出成功接口
 *  当ajax登出时，不进行跳转
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@Slf4j
@RestBody
@RestController(Constants.appName + ".LogoutController")
@Api(value="登出控制器",tags={"登出控制器"})
public class LogoutController implements LogoutApi {

    @Autowired
    private OnlineUserService onlineUserService;

    @Autowired
    private UserApi userApi;

    @Override
    public void logout(String stoken) {

        if( StringUtils.isEmpty(stoken) )
        {
            stoken = TokenHelper.getToken();
        }

        if( StringUtils.isNotEmpty(stoken) )
        {
            IOnlineUser onlineUser = onlineUserService.getById(extractKey(stoken));
            if( onlineUser != null )
            {
                pubLogoutEvent(onlineUser);
            }
            onlineUserService.remoteToken(stoken);
        }
    }

    private void pubLogoutEvent(IOnlineUser onlineUser)
    {
        UserLogoutEvent logoutEvent = new UserLogoutEvent();
        logoutEvent.setUserId(onlineUser.getUserId());
        logoutEvent.setType(UserLogoutEvent.TYPE_LOGOUT);
        logoutEvent.setClientId(onlineUser.getClientId());
        logoutEvent.setToken(onlineUser.getToken());
        logoutEvent.setAppCode(onlineUser.getAppCode());
        IUser user = userApi.getById(logoutEvent.getUserId());
        if( user != null )
        {
            log.info("client:[{}] ,用户[{}] ,token:[{}] logout!",logoutEvent.getClientId(),user.getAccount(),logoutEvent.getToken());
        }

        EventPublisherHelper.publish(logoutEvent);
    }

    private String extractKey(String token)
    {
        return UserLoginEvent.LOGINTYPE_CLIENT + "_" + token;
    }
}
