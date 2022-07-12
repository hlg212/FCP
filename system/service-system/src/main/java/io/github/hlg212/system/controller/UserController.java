package io.github.hlg212.system.controller;

import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.FworkHelper;
import io.github.hlg212.system.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RestBody
@RestController(Constants.appName+".UserController")
@RequestMapping("/user")
@Api(value="用户控制器",tags={"用户控制器"})
public class UserController {

    @Autowired
    private UserApi userApi;
    
    @RequestMapping(value="/changePassword",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation("修改当前登录用户密码")
	@ApiImplicitParams({
		@ApiImplicitParam( name = "oldPassword", value = "老密码", required = false),
		@ApiImplicitParam( name = "newPassword", value = "新密码", required = false),
        @ApiImplicitParam( name = "encodePassword", value = "密码编码", required = false),
        @ApiImplicitParam( name = "encode", value = "是否编码", required = false)
	})
    public void changePassword(String oldPassword, String newPassword,String encodePassword,String encode){
        boolean isEncode = StringUtils.isNotEmpty(encode) && !"false".equals(encode);
        if( isEncode )
        {
            try {
                String decodeStr = new String(Base64Utils.decodeFromString(encodePassword),"utf-8");
                String strs[] = decodeStr.split(" ");
                oldPassword = strs[0];
                newPassword = strs[1];
            } catch (UnsupportedEncodingException e) {
                ExceptionHelper.throwBusinessException("密码不正确!");
            }
        }

        userApi.changePassword(FworkHelper.getUid(), URLEncoder.encode(oldPassword),URLEncoder.encode(newPassword));
    }
}
