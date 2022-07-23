package io.github.hlg212.task.controller;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.task.model.bo.JobLogBo;
import io.github.hlg212.task.model.qco.JobLogQco;
import io.github.hlg212.task.service.JobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/jobLog")
@Api(value="任务日志管理控制器",tags={"任务日志管理"})
public class JobLogController implements CurdController<JobLogBo, JobLogQco> {
    @Autowired
    private JobLogService jobLogService;

    @ResponseBody
    @ApiOperation("获取日志详情")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public String clear(int jobGroup, int jobId, int type){

        return jobLogService.clear(jobGroup, jobId, type);
    }
}
