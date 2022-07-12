package io.github.hlg212.cas.controller;

import io.github.hlg212.cas.util.ExceptionMap;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.model.basic.User;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.FworkHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="rest方式登录控制器",tags={"rest方式登录控制器"})
@RequestMapping(value = "/loginRest")
@Slf4j
public class LoginRestController {


    @ApiOperation(value = "rest方式，登录成功之后，返回用户信息")
    @RequestMapping(value = "/succ",method = {RequestMethod.GET,RequestMethod.POST} )
    public IUser succ() {
        User user = (User) FworkHelper.getUser();
       if( user != null )
       {
           user.setPassword(null);
       }
       return user;
    }

    @ApiOperation(value = "rest方式,失败返回异常信息")
    @RequestMapping(value = "/fail",method = {RequestMethod.GET,RequestMethod.POST} )
    public void  fail() {
        Throwable t = (Throwable)HttpServletHelper.getRequest().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if( t != null ) {
                String msg = ExceptionMap.getExceptionMsg(t);

                if (StringUtils.isEmpty(msg)) {
                    msg = "账号密码错误";
                }
            if (t instanceof Exception) {
                ExceptionHelper.throwBusinessException(msg);
                log.debug("登录失败,", t);
            } else {
                ExceptionHelper.throwServerException(msg);
                log.error("登录失败,", t);
            }
        }
    }

}
