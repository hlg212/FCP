package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.*;
import io.github.hlg212.basic.model.qco.UserRoleQco;
import io.github.hlg212.basic.service.UserResService;
import io.github.hlg212.basic.service.UserRoleService;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.web.controller.CurdController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 
 * 用户-角色Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/userRole")
@Api(value="用户-角色控制器",tags={"用户-角色"})
public class UserRoleController implements CurdController<UserRoleBo, UserRoleQco> {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserResService userResService;

    @ResponseBody
    @ApiOperation("保存用户角色信息")
    @RequestMapping(value="/saveRoles",method={RequestMethod.POST})
    public void saveRoles(@RequestParamOrBody UserRoleSaveBo bo){
        userRoleService.saveRoles(bo);
    }

    @ResponseBody
    @ApiOperation("获取当前角色拥有的资源树")
    @RequestMapping(value="/getRoles",method={RequestMethod.POST,RequestMethod.GET})
    public List<RoleBo> getRoles(String userId)
    {
        return userRoleService.getRoles(userId);
    }


    @ResponseBody
    @ApiOperation("获取当前用户拥有的资源树")
    @RequestMapping(value="/getResTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<ResBo> getResTree(String userId,String roleType,String resCategory)
    {
        return userResService.getResTree(userId,roleType,resCategory);
    }
}
