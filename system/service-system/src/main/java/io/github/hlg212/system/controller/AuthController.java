package io.github.hlg212.system.controller;

import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.fcf.api.AuthApi;
import io.github.hlg212.fcf.api.OAuthApi;
import io.github.hlg212.fcf.model.basic.IApp;
import io.github.hlg212.fcf.model.basic.IRes;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.model.oauth.OAuthResult;
import io.github.hlg212.fcf.util.AppContextHelper;
import io.github.hlg212.fcf.util.FworkHelper;
import io.github.hlg212.fcf.util.OAuthApiHelper;
import io.github.hlg212.fcf.web.util.TokenHelper;
import io.github.hlg212.system.bo.UserInfoBo;
import io.github.hlg212.system.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestBody
@RestController(Constants.appName+".AuthController")
@RequestMapping("/auth")
@Api(value="权限控制器",tags={"权限控制器"})
public class AuthController {

    @Autowired
    private AuthApi authApi;


    @RequestMapping(value="/getAppPermissions",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation("获取用户权限列表")
    public List<String> getAppPermissions(String appCode)
    {
        if(StringUtils.isBlank(appCode)){
            appCode = AppContextHelper.getAppCode();
        }
        return authApi.getAppPermissions(appCode, FworkHelper.getUid());
    }

    @RequestMapping(value="/getMenuTree",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="获取用户的菜单资源",notes = "树形结构")
    public List<IRes>  getMenuTree(String appCode)
    {
        if(StringUtils.isBlank(appCode)){
            appCode = AppContextHelper.getAppCode();
        }
        return authApi.getMenuTree(appCode, FworkHelper.getUid());
    }


    @RequestMapping(value="/getApps",method={RequestMethod.GET,RequestMethod.POST})
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "type", value = "应用类型")
            }
    )
    @ApiOperation("获取用户的应用列表")
    public List<IApp> getApps(String type)
    {
        return authApi.getApps(FworkHelper.getUid(),null);
    }


    @RequestMapping(value="/getUser",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation("获取当前登录用户")
    public IUser getUser()
    {
        return FworkHelper.getUser();
    }

    @RequestMapping(value="/getUserInfo",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation("获取当前登录用户的权限等相关信息")
    public UserInfoBo getUserInfo(String appCode)
    {
        UserInfoBo bo = new UserInfoBo();
        IUser user = getUser();
        bo.setUser(user);
        bo.setPermissions(getAppPermissions(appCode));

        return bo;
    }

    @RequestMapping(value="/getToken",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation("获取当前登录用户的token")
    public String getToken()
    {
        return TokenHelper.getToken();
    }

}
