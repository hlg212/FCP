package io.github.hlg212.system.api.client;

import io.github.hlg212.fcf.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId = io.github.hlg212.system.util.Constants.appName + Constants.ApiContextId.LogoutApi,name=Constants.ApiName.LogoutApi,path =Constants.ApiPath.LogoutApi,url =Constants.ApiUrl.LogoutApi)
@RequestMapping("/logout")
public interface CasLogoutApi {

    @RequestMapping(method=RequestMethod.GET)
    public void logoutSession(@CookieValue(value = "CASSESSION",required = false)String session);
}
