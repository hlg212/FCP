package io.github.hlg212.gatewayAdmin.api;

import io.github.hlg212.fcf.api.RouteApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.gatewayAdmin.model.bo.GaRouteBo;
import io.github.hlg212.gatewayAdmin.model.qco.GaRouteQco;
import io.github.hlg212.gatewayAdmin.service.GaRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "路由api控制器", tags = {"路由api"})
@CacheConfig(cacheNames = Constants.Route)
public class RouteApiController implements RouteApi<GaRouteBo> {

    @Autowired
    private GaRouteService gaRouteService;

    @Override
    @Cacheable(key = Constants.RouteKey.getAllRoutes_spel)
    public List<GaRouteBo> getAllRoutes() {
        GaRouteQco qco = new GaRouteQco();
        return gaRouteService.find(qco);

    }

    @ResponseBody
    @ApiOperation("根据主键获取实体信息")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, paramType = "query")
    @Override
    public GaRouteBo getById(String id) {
        return gaRouteService.getById(id);
    }
}
