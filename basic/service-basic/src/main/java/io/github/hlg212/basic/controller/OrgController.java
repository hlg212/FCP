package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.OrgBo;
import io.github.hlg212.basic.model.qco.OrgQco;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * 机构Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/org")
@Api(value="机构控制器",tags={"机构"})
public class OrgController implements CurdieController<OrgBo, OrgQco> {
	
}
