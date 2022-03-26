package ${controllerPackage};

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hlg212.fcf.web.controller.CurdController;
import ${modelBoPackage}.${poModel}Bo;
import ${modelQcoPackage}.${poModel}Qco;

/** 
 * ${comment}Controller
 *
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequestMapping("/${poModelRequestMapping}")
@Api(value="${comment}控制器",tags={"${comment}"})
public class ${poModel}Controller implements CurdController<${poModel}Bo, ${poModel}Qco> {
	
}
