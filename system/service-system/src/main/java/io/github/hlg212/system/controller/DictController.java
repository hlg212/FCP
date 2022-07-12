package io.github.hlg212.system.controller;

import io.github.hlg212.fcf.annotation.RestBody;
import io.github.hlg212.fcf.model.basic.IDict;
import io.github.hlg212.fcf.util.DictHelper;
import io.github.hlg212.system.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestBody
@RestController(Constants.appName+".DictController")
@RequestMapping("/dict")
@Api(value="字典服务控制器",tags={"字典服务控制器"})
public class DictController {

    @RequestMapping(value="/getDicts",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="获取所有的字典")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "code", value = "服务代码", required = true)
    })
    public List<IDict> getDicts(String code) {
        if(StringUtils.isNotBlank(code)){
            return DictHelper.getAllDicts(code);
        }
        return DictHelper.getAllDicts();
    }

    @RequestMapping(value="/getDictVal",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="获取单个字典值")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "key", value = "字典编号", required = true),
            @ApiImplicitParam( name = "appCode", value = "服务代码", required = true)
    })
    public String getDicVal(String key,String appCode)
    {
        return DictHelper.getDictVal(key,appCode);
    }

    @RequestMapping(value="/getDictName",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="获取单个字典名称")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "key", value = "字典编号", required = true),
            @ApiImplicitParam( name = "appCode", value = "服务代码", required = true)
    })
    public String getDictName(String key,String appCode)
    {
        return DictHelper.getDictName(key, appCode);
    }

    @RequestMapping(value="/getDictMapVal",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="获取子集字典值")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "key", value = "字典编号", required = true),
            @ApiImplicitParam( name = "appCode", value = "服务代码", required = true)
    })
    public Map<String, String> getDictMapVal(String key,String appCode)
    {
        return DictHelper.getDictMapVal(key,appCode);
    }

    @RequestMapping(value="/getDictMapName",method={RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="获取子集字典名称")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "key", value = "字典编号", required = true),
            @ApiImplicitParam( name = "appCode", value = "服务代码", required = true)
    })
    public Map<String, String> getDictMapName(String key,String appCode)
    {
        return DictHelper.getDictMapName(key,appCode);
    }

}
