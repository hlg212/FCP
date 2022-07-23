package io.github.hlg212.dam.controller;

import io.github.hlg212.dam.model.bo.DamConfigBo;
import io.github.hlg212.dam.model.qco.DamConfigQco;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/damConfig")
@Api(value="数据权限模块配置",tags={"数据权限模块配置"})
public class DamConfigController implements CurdieController<DamConfigBo, DamConfigQco> {


}
