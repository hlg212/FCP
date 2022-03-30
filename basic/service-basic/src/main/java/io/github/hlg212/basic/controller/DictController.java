package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.DictBo;
import io.github.hlg212.basic.model.qco.DictQco;

/** 
 * 字典Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/dict")
@Api(value="字典控制器",tags={"字典"})
public class DictController implements CurdController<DictBo, DictQco> {
	
}
