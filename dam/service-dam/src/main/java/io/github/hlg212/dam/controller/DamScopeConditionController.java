package io.github.hlg212.dam.controller;

import io.github.hlg212.dam.model.bo.DamScopeConditionBo;
import io.github.hlg212.dam.model.qco.DamScopeConditionQco;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/damScopeCondition")
@Api(value="数据权限范围条件",tags={"数据权限范围条件"})
public class DamScopeConditionController implements CurdieController<DamScopeConditionBo, DamScopeConditionQco> {

}
