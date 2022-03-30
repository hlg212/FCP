package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.ClientBo;
import io.github.hlg212.basic.model.qco.ClientQco;

/** 
 * 客户端Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/client")
@Api(value="客户端控制器",tags={"客户端"})
public class ClientController implements CurdController<ClientBo, ClientQco> {
	
}
