package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.service.AuthService;
import io.github.hlg212.fcf.api.AuthApi;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(value = "权限api控制器", tags = {"权限api控制器"})
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.Auth)
@AllArgsConstructor
public class AuthApiController implements AuthApi {

    private final AuthService authService;


    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAppPermissions_spel)
    public List<String> getAppPermissions(String appCode, String userId) {
        return authService.getAppPermissions(appCode, userId);
    }

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAllPermissions_spel)
    public List<String> getAllPermissions(String userId) {
        return authService.getAllPermissions(userId);
    }


    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getMenuTree_spel)
    public List<ResBo> getMenuTree(String appCode, String userId) {

        return authService.getMenuTree(appCode, userId);
    }


    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAllAuthRes_spel)
    public List<ResBo> getAllAuthRes() {
        return authService.getAllAuthRes();
    }

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getApps_spel)
    public List<AppBo> getApps(String userId, String type) {
        return authService.getAppsByUserId(userId, type);
    }
}
