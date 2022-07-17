package io.github.hlg212.task.controller;

import io.github.hlg212.fcf.web.controller.CurdController;
import io.github.hlg212.task.model.bo.JobInfoBo;
import io.github.hlg212.task.model.qco.JobInfoQco;
import io.github.hlg212.task.service.JobInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/jobInfo")
@Api(value="定时任务控制器",tags={"定时任务管理"})
public class JobInfoController implements CurdController<JobInfoBo, JobInfoQco> {
    @Autowired
    private JobInfoService jobInfoService;

    @ResponseBody
    @ApiOperation("开始")
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start(@RequestParam String id) {
        log.debug("开始定时任务.{}", id);
        jobInfoService.start(id);
    }

    @ResponseBody
    @ApiOperation("结束")
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void stop(@RequestParam String id) {
        log.debug("结束定时任务.{}", id);
        jobInfoService.stop(id);
    }

    @ResponseBody
    @ApiOperation("立即执行一次")
    @RequestMapping(value = "/trigger", method = RequestMethod.GET)
    public void trigger(@RequestParam String id, @RequestParam String param) {
        log.debug("立即执行一次定时任务.{}", id);
        jobInfoService.trigger(id, param);
    }
}
