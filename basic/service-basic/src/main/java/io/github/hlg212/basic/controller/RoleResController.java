package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.bo.RoleResBo;
import io.github.hlg212.basic.model.bo.RoleResSaveBo;
import io.github.hlg212.basic.model.qco.RoleResQco;
import io.github.hlg212.basic.service.RoleResService;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** 
 * 角色资源Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/roleRes")
@Api(value="角色资源控制器",tags={"角色资源"})
public class RoleResController implements CurdieController<RoleResBo, RoleResQco> {

    @Autowired
    private RoleResService roleResService;

    @ResponseBody
    @ApiOperation("保存角色资源")
    @RequestMapping(value="/saveRoleRes",method={RequestMethod.POST})
    public void saveRoleRes(@RequestParamOrBody RoleResSaveBo roleResSaveBo){
        roleResService.saveRoleRes(roleResSaveBo.getRoleId(),roleResSaveBo.getResIds(),roleResSaveBo.getDelResIds());
    }

    @ResponseBody
    @ApiOperation("获取app的资源树,app伪装成顶级节点")
    @RequestMapping(value="/getAppResTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<ResBo> getAppResTree(String resCategory)
    {
        return roleResService.getAppResTree(resCategory);
    }

    @ResponseBody
    @ApiOperation("获取角色的app的资源树,app伪装成顶级节点")
    @RequestMapping(value="/getAppRoleResTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<ResBo> getAppRoleResTree(String roleId,String resCategory)
    {
        return roleResService.getAppRoleResTree(roleId,resCategory);
    }

    @ResponseBody
    @ApiOperation("获取当前角色拥有的资源树")
    @RequestMapping(value="/getRoleResTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<ResBo> getRoleResTree(String roleId,String resCategory)
    {
        return roleResService.getRoleResTree(roleId,resCategory);
    }

    @ResponseBody
    @ApiOperation("获取当前角色拥有的资源id列表")
    @RequestMapping(value="/getRoleResIds",method={RequestMethod.POST,RequestMethod.GET})
    public List<String>  getRoleResIds(String roleId, String resCategory)
    {
        List<ResBo> list = roleResService.getRoleRes(roleId,resCategory);
        List<String> ids = new ArrayList<>(list.size());
        CollectionHelper.getPropertyValues(list,"id",ids);
        return ids;
    }
}
