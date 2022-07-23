package io.github.hlg212.task.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.hlg212.fcf.api.common.TaskApi;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.FeignClientContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 任务桥接执行器
 * 该执行器将XXL-JOB的调度转为FCP任务；
 *
 * @author huangligui
 * @since 2022/7/23 16:00
 */
@Component
@Slf4j
public class JobExecutor{

    @XxlJob("taskJobExecutor")
	public void taskJobExecutor() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        String param = XxlJobHelper.getJobParam();

        if(StringUtils.isBlank(param)){
            ExceptionHelper.throwBusinessException("参数param为空,不是FCP任务类型！");
        }
        String[] params = param.split(";");
        if(params.length < 2){
            ExceptionHelper.throwBusinessException("参数param格式不对，格式为：系统名;bean名;String类型参数");
        }
        TaskApi taskApi = FeignClientContextUtils.getBean(TaskApi.class, params[0]);
        String result = "";
        try {
            if (params.length == 2) {
                result = taskApi.execute(params[1], "");
            }
            if (params.length == 3) {
                result = taskApi.execute(params[1], params[2]);
            }
        }catch (Exception e)
        {
            log.warn("系统名[{}],任务[{}],执行失败;{}",params[0],params[1],e.getMessage());
            XxlJobHelper.log(e);
            ExceptionHelper.throwBusinessException(e.getMessage());
        }
	}

}
