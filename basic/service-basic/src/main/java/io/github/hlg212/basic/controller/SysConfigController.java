package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.SysConfigBo;
import io.github.hlg212.basic.model.qco.SysConfigQco;

/** 
 * 系统配置Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/sysConfig")
@Api(value="系统配置控制器",tags={"系统配置"})
public class SysConfigController implements CurdController<SysConfigBo, SysConfigQco> {
	
}
