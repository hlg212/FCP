package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.AppRoleBo;
import io.github.hlg212.basic.model.qco.AppRoleQco;

/** 
 * 应用-角色Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/appRole")
@Api(value="应用-角色控制器",tags={"应用-角色"})
public class AppRoleController implements CurdController<AppRoleBo, AppRoleQco> {
	
}
