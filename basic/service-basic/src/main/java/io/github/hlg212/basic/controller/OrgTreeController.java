package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.OrgTreeBo;
import io.github.hlg212.basic.model.qco.OrgTreeQco;

/** 
 * 机构树Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/orgTree")
@Api(value="机构树控制器",tags={"机构树"})
public class OrgTreeController implements CurdController<OrgTreeBo, OrgTreeQco> {
	
}
