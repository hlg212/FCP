package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.*;
import io.github.hlg212.basic.service.ClientRoleService;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.qco.ClientRoleQco;

import java.util.List;

/** 
 * 客户端-角色Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/clientRole")
@Api(value="客户端-角色控制器",tags={"客户端-角色"})
@AllArgsConstructor
public class ClientRoleController implements CurdController<ClientRoleBo, ClientRoleQco> {

    private final ClientRoleService clientRoleService;

    @ResponseBody
    @ApiOperation("获取当前角色拥有的资源树")
    @RequestMapping(value="/getRoles",method={RequestMethod.POST,RequestMethod.GET})
    public List<RoleBo> getRoles(String clientId)
    {
        return clientRoleService.getRoles(clientId);
    }

    @ResponseBody
    @ApiOperation("获取当前角色拥有的资源树")
    @RequestMapping(value="/getResTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<ResBo> getResTree(String clientId,String resCategory)
    {
        return clientRoleService.getResTree(clientId,resCategory);
    }

    @ResponseBody
    @ApiOperation("保存客户端角色信息")
    @RequestMapping(value="/saveRoles",method={RequestMethod.POST})
    public void saveClientRoles(@RequestParamOrBody ClientRoleSaveBo bo){
        clientRoleService.saveClientRoles(bo);
    }



}
