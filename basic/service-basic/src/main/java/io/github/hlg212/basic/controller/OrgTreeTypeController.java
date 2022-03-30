package io.github.hlg212.basic.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.basic.model.bo.OrgTreeTypeBo;
import io.github.hlg212.basic.model.qco.OrgTreeTypeQco;

/** 
 * 机构树类型Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/orgTreeType")
@Api(value="机构树类型控制器",tags={"机构树类型"})
public class OrgTreeTypeController implements CurdController<OrgTreeTypeBo, OrgTreeTypeQco> {
	
}
