package io.github.hlg212.basic.controller;

import io.github.hlg212.basic.model.bo.DictBo;
import io.github.hlg212.basic.model.qco.DictQco;
import io.github.hlg212.basic.service.DictService;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.web.controller.CurdieController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 
 * 字典Controller
 *
 * @author huanglg
 * @date 2022-03-28
 */
@RestController
@RequestMapping("/dict")
@Api(value="字典控制器",tags={"字典"})
public class DictController implements CurdieController<DictBo, DictQco> {

    @ResponseBody
    @ApiOperation("获取字典树")
    @RequestMapping(value="/findTree",method={RequestMethod.POST,RequestMethod.GET})
    public List<DictBo> findTree(@RequestParamOrBody DictQco qco)
    {
        return getService(DictService.class).findTree(qco);
    }
}
