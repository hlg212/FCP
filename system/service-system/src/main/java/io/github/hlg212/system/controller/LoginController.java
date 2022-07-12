package io.github.hlg212.system.controller;

import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.fcf.model.oauth.OAuthResult;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.OAuthApiHelper;
import io.github.hlg212.fcf.web.util.SsoTokenHelper;
import io.github.hlg212.system.bo.CodeLoginBo;
import io.github.hlg212.system.bo.PasswordLoginBo;
import io.github.hlg212.system.util.Constants;
import io.github.hlg212.system.util.OAuthTokenCacheHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


@RestBody
@Api(value = "登录控制器", tags = {"登录控制器"})
@Slf4j
@RestController(Constants.appName + ".LoginController")
@RequestMapping("/login")
public class LoginController {


    private final static String GRANT_TYPE_PASSWORD = "password";
    private final static String GRANT_TYPE_CODE = "authorization_code";

    @RequestMapping(value = "/checkToken", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("验证token")
    public void checkToken(String token) {
        try {
            OAuthTokenCacheHelper.checkToken(token);
        } catch (Exception e) {
            log.warn("验证token失败!", e);
            ExceptionHelper.throwBusinessException("验证token失败!");
        }

    }

    @RequestMapping(value = "/sso", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("单点登录,会将Token存储到session，已达到单点登陆")
    @ResponseBody
    public String ssoLogin(@RequestParamOrBody PasswordLoginBo bo) {
        String token = cusLogin(bo);

        SsoTokenHelper.writeToken(token);
        return token;
    }

    private String login(String usernamePassword, String appCode) {
        String decodeStr = new String(Base64Utils.decodeFromString(usernamePassword), StandardCharsets.UTF_8);
        String[] strs = decodeStr.split(" ");
        return login(strs[0], strs[1], appCode);
    }

    private String login(String username, String password, String appCode) {
        OAuthResult result = null;
        try {
            result = OAuthApiHelper.login(username, password, GRANT_TYPE_PASSWORD, appCode);
        } catch (Exception e) {
            log.warn("账号密码错误!", e);
            ExceptionHelper.throwBusinessException("账号密码错误!");
        }

        if (result != null) {
            if (StringUtils.isNotEmpty(result.getError())) {
                ExceptionHelper.throwBusinessException(result.getErrorDescription());
            }
            return result.getAccessToken();
        }
        return null;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("登录,自定义登录时使用")
    @ResponseBody
    public String cusLogin(@RequestParamOrBody PasswordLoginBo bo) {
        boolean isEncode = StringUtils.isNotEmpty(bo.getIsEncode()) && !"false".equals(bo.getIsEncode());
        String token;
        if (isEncode) {
            token = login(bo.getUsernamePassword(), bo.getIsEncode());
        } else {
            token = login(bo.getUsername(), bo.getPassword(), bo.getAppCode());
        }
        return token;
    }


    @RequestMapping(value = "/code", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("使用code模式登录")
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "code", value = "授权码", required = true),
                    @ApiImplicitParam(name = "redirectUri", value = "申请授权码时的跳转地址", required = true),
                    @ApiImplicitParam(name = "appCode", value = "使用的应用编码")
            }
    )
    @ResponseBody
    public String codeLogin(@RequestParamOrBody CodeLoginBo bo) {
        OAuthResult result = null;
        try {
            result = OAuthApiHelper.codeLogin(bo.getRedirectUri(), bo.getCode(), GRANT_TYPE_CODE, bo.getAppCode());
        } catch (Exception e) {
            log.warn("获取token失败!", e);
            ExceptionHelper.throwBusinessException("获取token失败!");
        }

        if (result != null) {
            if (StringUtils.isNotEmpty(result.getError())) {
                ExceptionHelper.throwBusinessException(result.getErrorDescription());
            }
            SsoTokenHelper.writeToken(result.getAccessToken());
            return result.getAccessToken();
        }

        return null;
    }

}
