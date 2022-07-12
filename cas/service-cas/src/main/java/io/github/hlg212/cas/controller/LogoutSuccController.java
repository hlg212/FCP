package io.github.hlg212.cas.controller;

import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.annotation.RestBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:  登出成功接口
 *  当ajax登出时，不进行跳转
 *
 * @author: huangligui
 * @create: 2018-10-26 10:30
 **/
@RestBody
@RestController(Constants.appName + ".LogoutSuccController")
@Api(value="登出控制器",tags={"登出控制器"})
@RequestMapping("/logoutSucc" )
public class LogoutSuccController {

    @RequestMapping(method={RequestMethod.GET,RequestMethod.POST})
    public void logoutSucc() {

    }
}
