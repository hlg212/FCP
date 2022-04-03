package io.github.hlg212.gatewayAdmin.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.gatewayAdmin.model.bo.GaAnonymousResBo;
import io.github.hlg212.gatewayAdmin.model.qco.GaAnonymousResQco;

/** 
 * 应用Controller
 *
 * @author huanglg
 * @date 2022-04-03
 */
@RestController
@RequestMapping("/gaAnonymousRes")
@Api(value="应用控制器",tags={"应用"})
public class GaAnonymousResController implements CurdController<GaAnonymousResBo, GaAnonymousResQco> {
	
}
