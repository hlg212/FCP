package io.github.hlg212.task.api.client;

import io.github.hlg212.task.api.Constants;
import io.github.hlg212.task.conf.XxlAuthConfiguration;
import io.github.hlg212.task.xxl.model.XxlJobLog;
import io.github.hlg212.task.xxl.model.XxlPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.JobLogCenterApi,name=Constants.ApiName.JobLogCenterApi, path=Constants.ApiPath.JobLogCenterApi, url=Constants.ApiUrl.JobLogCenterApi, configuration = XxlAuthConfiguration.class)
@RequestMapping(Constants.ApiMapping.JobLogCenterApi)
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
    public XxlPageResult<XxlJobLog> findPage(@RequestParam("start") int start,
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
