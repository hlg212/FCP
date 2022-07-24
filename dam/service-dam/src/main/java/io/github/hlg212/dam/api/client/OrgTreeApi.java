package io.github.hlg212.dam.api.client;

import io.github.hlg212.basic.model.bo.OrgTreeBo;
import io.github.hlg212.basic.model.bo.OrgTreeParam;
import io.github.hlg212.dam.api.Constants;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(contextId = Constants.ApiContextId.OrgTreeApi,name=Constants.ApiName.OrgTreeApi,path =Constants.ApiPath.OrgTreeApi,url =Constants.ApiUrl.OrgTreeApi)
@RequestMapping(Constants.ApiMapping.OrgTreeApi)
public interface OrgTreeApi {

    @ApiOperation("获取机构树子节点,集合方式返回")
    @RequestMapping(value="/getChildList",method={RequestMethod.GET})
    List<OrgTreeBo> getChildList(@RequestParamOrBody OrgTreeParam param);
}
