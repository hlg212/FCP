package io.github.hlg212.task.api.client;

import io.github.hlg212.task.api.Constants;
import io.github.hlg212.task.conf.XxlAuthConfiguration;
import io.github.hlg212.task.xxl.model.XxlJobInfo;
import io.github.hlg212.task.xxl.model.XxlPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = Constants.ApiContextId.JobCenterApi,name=Constants.ApiName.JobCenterApi, path=Constants.ApiPath.JobCenterApi, url=Constants.ApiUrl.JobCenterApi, configuration = XxlAuthConfiguration.class)
@RequestMapping(Constants.ApiMapping.JobCenterApi)
public interface JobCenterApi {
    /**
     * 获取分页列表
     * @param start
     * @param length
     * @param jobGroup
     * @param jobDesc
     * @param executorHandler
     * @param filterTime
     * @return
     */
    @RequestMapping(value="/pageList", method= RequestMethod.POST)
    public XxlPageResult<XxlJobInfo> findPage(@RequestParam("start") int start,
                                              @RequestParam("length") int length,
                                              @RequestParam("jobGroup") int jobGroup,
                                              @RequestParam("triggerStatus") int triggerStatus,
                                              @RequestParam("jobDesc") String jobDesc,
                                              @RequestParam("executorHandler") String executorHandler,
                                              @RequestParam("filterTime") String filterTime);

    /**
     * 新增任务
     * @param map
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String add(MultiValueMap<String, Object> map);

    /**
     * 修改任务
     * @param map
     * @return
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String update(MultiValueMap<String, Object> map);

    /**
     * 删除任务
     * @param id
     * @return
     */
    @RequestMapping(value="/remove", method=RequestMethod.GET)
    public String remove(@RequestParam("id") String id);

    /**
     * 开始任务
     * @param id
     * @return
     */
    @RequestMapping(value="/start", method=RequestMethod.GET)
    public String start(@RequestParam("id") String id);

    /**
     * 停止任务
     * @param id
     * @return
     */
    @RequestMapping(value="/stop", method=RequestMethod.GET)
    public String stop(@RequestParam("id") String id);

    /**
     * 执行一次
     * @param id
     * @param param
     * @return
     */
    @RequestMapping(value="/trigger", method=RequestMethod.GET)
    public String trigger(@RequestParam("id") String id, @RequestParam("param") String param);

}
