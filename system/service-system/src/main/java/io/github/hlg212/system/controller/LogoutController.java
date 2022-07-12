package io.github.hlg212.system.controller;

import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.fcf.api.LogoutApi;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import io.github.hlg212.fcf.web.util.SsoTokenHelper;
import io.github.hlg212.system.api.client.CasLogoutApi;
import io.github.hlg212.system.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;


@RestBody
@RestController(Constants.appName + ".LogoutController")
@RequestMapping("/logout")
@Api(value = "登出控制器", tags = {"登出控制器"})
public class LogoutController {

    @Value("${fcf.logoutUri}")
    private String logoutUri;

    @Autowired
    private LogoutApi logoutApi;
    @Autowired
    private CasLogoutApi casLogoutApi;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "redirect", value = "登出之后定向的地址"),
                    @ApiImplicitParam(name = "appCode", value = "系统编码", required = true),
                    @ApiImplicitParam(name = "token", value = "token")
            }
    )
    @ApiOperation("登出,跳转登出")
    public void logout(String redirect, String appCode, String token) {
        SsoTokenHelper.clearToken();
        logoutApi.logout(token);

        if (StringUtils.isEmpty(redirect)) {
            redirect = HttpServletHelper.getBasePath();
        }
        String logout = logoutUri + "?appCode=" + appCode + "&redirect=" + redirect;
        try {
            HttpServletHelper.getResponse().sendRedirect(logout);
        } catch (Exception e) {
            ExceptionHelper.throwServerException("登出失败：" + e.getMessage());
        }

    }

    @RequestMapping(value = "/ajax", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "登出,ajax", notes = "该接口一般配合 /frame/login 接口，只会清除token,而不会清除认证中心session。")
    public void ajax() {
        logoutApi.logout(null);
        String session = getValue("CASSESSION");
        if (StringUtils.isNotEmpty(session)) {
            casLogoutApi.logoutSession(session);
        }
        SsoTokenHelper.clearToken();
    }


    private String getValue(String key) {
        Cookie[] cookies = HttpServletHelper.getRequest().getCookies();
        String value = null;
        if (null != cookies && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (key.equals(c.getName())) {
                    value = c.getValue();
                    break;
                }
            }
        }

        return value;
    }
}
