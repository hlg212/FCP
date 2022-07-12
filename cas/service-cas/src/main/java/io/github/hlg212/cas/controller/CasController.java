package io.github.hlg212.cas.controller;

import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.model.basic.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@RestController
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.UserInfo)
@Api(value="认证中心控制器",tags={"认证中心管理"})
public class CasController {
    @Autowired
    private UserApi userApi;

    @ApiOperation(value = "获取用户信息，根据token获取用户信息")
    @RequestMapping(value = "/userinfo",method = {RequestMethod.GET,RequestMethod.POST} )
    @Cacheable(key = "#p0.details.tokenValue")
    public <E extends IUser> E  user(@ApiIgnore Principal principal) {

        if( principal instanceof OAuth2Authentication)
        {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication)principal;
            if(Constants.GrantType.CLIENT_CREDENTIALS.getValue().equals(auth2Authentication.getOAuth2Request().getGrantType()))
            {
                // 将客户端有用户绑定
                return (E)userApi.getById(principal.getName());
            }
        }

        User  user = (User)userApi.getUserByAccount(principal.getName());
        user.setPassword(null);
        return (E) user;

    }

    @ApiOperation("导航到欢迎页面")
    @RequestMapping(value = "/index/welcome" ,method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView welcome() {
        return new ModelAndView("/welcome");
    }
}
