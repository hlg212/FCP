package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.UserBo;
import io.github.hlg212.basic.model.qco.UserQco;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * 用户Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/user")
@Api(value="用户控制器",tags={"用户"})
public class UserController implements CurdieController<UserBo, UserQco> {
	
}
