package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.ClientRoleBo;
import io.github.hlg212.basic.model.qco.ClientRoleQco;

/** 
 * 客户端-角色Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/clientRole")
@Api(value="客户端-角色控制器",tags={"客户端-角色"})
public class ClientRoleController implements CurdController<ClientRoleBo, ClientRoleQco> {
	
}
