package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.basic.service.ResService;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.web.controller.CurdController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 
 * 资源Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/res")
@Api(value="资源控制器",tags={"资源"})
public class ResController implements CurdController<ResBo, ResQco> {

    @ResponseBody
    @ApiOperation("获取资源树")
    @RequestMapping(value="/findTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<ResBo> findTree(@RequestParamOrBody ResQco qco)
    {
        return getService(ResService.class).findTree(qco);
    }
}
