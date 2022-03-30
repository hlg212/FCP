package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.OrgRoleBo;
import io.github.hlg212.basic.model.qco.OrgRoleQco;

/** 
 * 机构-角色Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/orgRole")
@Api(value="机构-角色控制器",tags={"机构-角色"})
public class OrgRoleController implements CurdController<OrgRoleBo, OrgRoleQco> {
	
}
