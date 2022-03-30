package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.qco.AppQco;
import io.github.hlg212.fcf.api.AppApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.fcf.model.QueryParam;
import io.github.hlg212.fcf.web.controller.QueryController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value="应用api控制器",tags={"应用api控制器"})
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.App)
public class AppApiController implements QueryController<AppBo, AppQco> ,AppApi<AppBo> {


    @ResponseBody
    @ApiOperation("根据code获取实体信息")
    @ApiImplicitParam(name="id", value="主键ID", required=true, paramType="query")
    @Override
    @Cacheable(key =  Constants.AppKey.getAppByCode_spel)
    public AppBo getAppByCode(String code) {
        AppQco q = new AppQco();
        q.addCondition("code",QueryParam.EQUALS,code);
        return this.get(q);
    }

    @ResponseBody
    @ApiOperation("根据主键获取实体信息")
    @ApiImplicitParam(name="id", value="主键ID", required=true, paramType="query")
    @Override
    @Cacheable(key = "#p0")
    public AppBo getById(String id) {
        return QueryController.super.getById(id);
    }
}
