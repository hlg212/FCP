package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.UserRoleBo;
import io.github.hlg212.basic.model.qco.UserRoleQco;

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
	
}
