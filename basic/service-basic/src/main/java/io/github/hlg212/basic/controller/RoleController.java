package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.RoleBo;
import io.github.hlg212.basic.model.qco.RoleQco;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * 角色Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/role")
@Api(value="角色控制器",tags={"角色"})
public class RoleController implements CurdieController<RoleBo, RoleQco> {


}
