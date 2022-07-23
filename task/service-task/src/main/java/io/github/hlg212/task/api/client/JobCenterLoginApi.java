package io.github.hlg212.task.api.client;

import io.github.hlg212.task.api.Constants;
import io.github.hlg212.task.xxl.model.XxlJobInfo;
import io.github.hlg212.task.xxl.model.XxlPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.JobCenterLoginApi,name=Constants.ApiName.JobCenterLoginApi, path=Constants.ApiPath.JobCenterLoginApi, url=Constants.ApiUrl.JobCenterLoginApi)
@RequestMapping(Constants.ApiMapping.JobCenterLoginApi)
public interface JobCenterLoginApi {

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@RequestParam("userName") String userName, @RequestParam("password") String password);

}
