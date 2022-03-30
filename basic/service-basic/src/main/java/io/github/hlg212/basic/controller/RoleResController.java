package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.RoleResBo;
import io.github.hlg212.basic.model.qco.RoleResQco;

/** 
 * 角色资源Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/roleRes")
@Api(value="角色资源控制器",tags={"角色资源"})
public class RoleResController implements CurdController<RoleResBo, RoleResQco> {
	
}
