package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.OrgTreeBo;
import io.github.hlg212.basic.model.bo.OrgTreeParam;
import io.github.hlg212.basic.model.bo.OrgTreeSaveBo;
import io.github.hlg212.basic.model.qco.OrgTreeQco;
import io.github.hlg212.basic.service.OrgTreeService;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 
 * 机构树Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/orgTree")
@Api(value="机构树控制器",tags={"机构树"})
public class OrgTreeController implements CurdieController<OrgTreeBo, OrgTreeQco> {

    @Autowired
    private OrgTreeService orgTreeService;

    @ApiOperation("保存机构树")
    @RequestMapping(value="/saveTree",method={RequestMethod.POST})
    public void saveTree(@RequestBody OrgTreeSaveBo bo)
    {
        getService(OrgTreeService.class).saveTree(bo);
    }

    @ApiOperation("获取机构树")
    @RequestMapping(value="/findTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<OrgTreeBo> findTree(@RequestParamOrBody OrgTreeQco qco)
    {
        return getService(OrgTreeService.class).findTree(qco);
    }

    @ApiOperation("获取机构树子节点,集合方式返回")
    @RequestMapping(value="/getChildList",method={RequestMethod.POST,RequestMethod.GET})
    public List<OrgTreeBo> getChildList(@RequestParamOrBody OrgTreeParam param)
    {
       return orgTreeService.getChildList(param);
    }

    @ApiOperation("获取机构树子节点，树方式返回")
    @RequestMapping(value="/getChildTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<OrgTreeBo> getChildTree(@RequestParamOrBody OrgTreeParam param)
    {
        return orgTreeService.getChildTree(param);
    }

    @ApiOperation("获取机构父级，集合方式，顶级在第一个位置")
    @RequestMapping(value="/getParentList",method={RequestMethod.POST,RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam( name = "orgTreeTypeCode", value = "机构树类型编码", required = true),
            @ApiImplicitParam( name = "orgId", value = "机构id", required = true),
            @ApiImplicitParam( name = "childLevel", value = "父级的(向上的)深度,不填就是所有", required = false)
    })
    public List<OrgTreeBo> getParentList(@RequestParamOrBody OrgTreeParam param)
    {
        return orgTreeService.getParentList(param);
    }

    @ApiOperation("获取机构父级，树方式，顶级在第一个位置")
    @RequestMapping(value="/getParentTree",method={RequestMethod.POST,RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam( name = "orgTreeTypeCode", value = "机构树类型编码", required = true),
            @ApiImplicitParam( name = "orgId", value = "机构id", required = true),
            @ApiImplicitParam( name = "childLevel", value = "父级的(向上的)深度,不填就是所有", required = false)
    })
    public List<OrgTreeBo> getParentTree(@RequestParamOrBody OrgTreeParam param)
    {
        return orgTreeService.getParentTree(param);
    }

    @ApiOperation("获取机构父级")
    @RequestMapping(value="/getParent",method={RequestMethod.POST,RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam( name = "orgTreeTypeCode", value = "机构树类型编码", required = true),
            @ApiImplicitParam( name = "orgId", value = "机构id", required = true)
    })
    public OrgTreeBo getParent(String orgTreeTypeCode,String orgId)
    {
        return orgTreeService.getParent(orgTreeTypeCode,orgId);
    }

}
