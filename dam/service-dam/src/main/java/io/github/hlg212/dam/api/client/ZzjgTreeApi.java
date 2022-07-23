package io.github.hlg212.dam.api.client;

import io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient(contextId = io.github.hlg212.dam.api.Constants.ApiContextId.ZzjgTreeApi,name=Constants.APP_APIGATEWAY_BASIC,path =Constants.APP_BASIC_PATH,url =Constants.AppFeignUrl.APP_BASIC)
@RequestMapping(io.github.hlg212.dam.api.Constants.ApiMapping.ZzjgTreeApi)
public interface ZzjgTreeApi {

   // @RequestMapping(value = "/getChilds", method = RequestMethod.GET)
    //public List<ZzjgTreeBo> getChilds(@RequestParam(value = "jglx")  String jglx, @RequestParam(value = "jgid") String jgid, @RequestParam(value = "dj") Integer dj );
}
