package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.qco.AppQco;

/** 
 * 应用Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/app")
@Api(value="应用控制器",tags={"应用"})
public class AppController implements CurdController<AppBo, AppQco> {
	
}
