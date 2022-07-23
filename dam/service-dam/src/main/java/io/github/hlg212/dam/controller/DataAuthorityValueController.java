package io.github.hlg212.dam.controller;

import io.github.hlg212.dam.service.DataAuthorityValueService;
import io.github.hlg212.dam.util.Constants;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.util.SpringHelper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "数据权限条件控制器", tags = {"数据权限条件控制器"})
@RequestMapping("/dataAuthorityValue")
@Slf4j
public class DataAuthorityValueController {

//    @RequestMapping(value="/previewAuthoritys", method = {RequestMethod.GET,RequestMethod.POST})
//    public Object previewAuthoritys(@RequestParamOrBody SjqxfwtjylBo sjqxfwtjylBo)
//    {
//        DataAuthorityValueService service = (DataAuthorityValueService) SpringHelper.getBean(sjqxfwtjylBo.getFwlx() + Constants.DATA_AUTHORITY_VALUE_SERVICE_SUFFIX);
//        Object val = service.getAuthoritys(sjqxfwtjylBo.getUserId(),sjqxfwtjylBo);
//        return val;
//    }

}
