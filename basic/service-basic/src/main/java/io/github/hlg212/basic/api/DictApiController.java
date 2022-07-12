
package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.DictBo;
import io.github.hlg212.basic.model.qco.DictQco;
import io.github.hlg212.basic.service.DictService;
import io.github.hlg212.fcf.api.AppApi;
import io.github.hlg212.fcf.api.DictApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.fcf.model.basic.IApp;
import io.github.hlg212.fcf.web.controller.QueryController;
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
@Api(value = "字典api控制器", tags = {"字典api控制器"})
@CacheConfig(cacheNames = Constants.Dict)
public class DictApiController implements QueryController<DictBo, DictQco>, DictApi<DictBo> {

    @Autowired
    private AppApi appApi;


    @Override
    @Cacheable(key = Constants.DictKey.getAllDicts_spel)
    public List<DictBo> getAllDicts(String appCode) {
        IApp app = appApi.getAppByCode(appCode);
        DictQco qco = new DictQco();
        if (app != null) {
            qco.setAppId(app.getId());
            return getService(DictService.class).findTree(qco);
        }
//        if (app != null) {
//            DictQco qco = new DictQco();
//            qco.setAppId(app.getId());
//            return find(qco);
//        }
        return null;
    }


    @ResponseBody
    @ApiOperation("根据主键获取实体信息")
    @ApiImplicitParam(name = "id", value = "主键ID", required = true, paramType = "query")
    @Cacheable(key = "#p0")
    @Override
    public DictBo getById(String id) {
        return QueryController.super.getById(id);
    }
}
