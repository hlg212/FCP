package io.github.hlg212.cas.api;

import io.github.hlg212.cas.bo.UserLoginLockBo;
import io.github.hlg212.cas.service.UserLoginLockService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.api.UserLockApi;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.web.controller.CurdController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController(Constants.appName + ".UserLockApiController")
@Api(value="用户锁定Api",tags={"用户锁定Api"})
public class UserLockApiController implements UserLockApi<UserLoginLockBo,Qco>,CurdController<UserLoginLockBo,Qco> {

    @ApiOperation("是否锁定")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "username", value = "用户账号", required = true )
    })
    @Override
    public boolean isLock(String username) {
       return getService(UserLoginLockService.class).isLock(username);
    }

}
