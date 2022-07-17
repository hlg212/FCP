package io.github.hlg212.task.api.client;

import io.github.hlg212.task.api.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(contextId = "JobLogCenterApi",name=Constants.APP_APIGATEWAY_XXL, path=Constants.XXL_PATH, url=Constants.AppFeignUrl.XXL_JOB)
@RequestMapping(Constants.ApiMapping.LOG)
public interface JobLogCenterApi {
    /**
     * 获取分页列表
     * @param start
     * @param length
     * @param jobGroup
     * @param jobId
     * @param logStatus
     * @param filterTime
     * @return
     */
    @RequestMapping(value="/pageList", method= RequestMethod.POST)
    public Map<String, Object> findPage(@RequestParam("start") int start,
                                        @RequestParam("length") int length,
                                        @RequestParam("jobGroup") int jobGroup,
                                        @RequestParam("jobId") int jobId,
                                        @RequestParam("logStatus") int logStatus,
                                        @RequestParam("filterTime") String filterTime);

    /**
     * 清除
     * @param jobGroup
     * @param jobId
     * @param type
     * @return
     */
    @RequestMapping(value="/clearLog", method=RequestMethod.POST)
    public String clearLog(@RequestParam("jobGroup") int jobGroup,
                           @RequestParam("jobId") int jobId,
                           @RequestParam("type") int type);

}
