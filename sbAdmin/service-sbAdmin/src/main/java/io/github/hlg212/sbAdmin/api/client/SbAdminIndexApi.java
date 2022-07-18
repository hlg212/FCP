package io.github.hlg212.sbAdmin.api.client;

import io.github.hlg212.sbAdmin.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId = "SbAdminIndexApi",name=Constants.APP_APIGATEWAY_SBADMIN, path=Constants.APP_SBADMIN_PATH,url = Constants.AppFeignUrl.APP_SBADMIN)
public interface SbAdminIndexApi {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index();

}
