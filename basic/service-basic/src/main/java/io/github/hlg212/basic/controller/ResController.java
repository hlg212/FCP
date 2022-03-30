package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ResQco;

/** 
 * 资源Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/res")
@Api(value="资源控制器",tags={"资源"})
public class ResController implements CurdController<ResBo, ResQco> {
	
}
