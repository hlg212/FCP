package io.github.hlg212.task.handler;

import io.github.hlg212.fcf.api.common.TaskApi;
import io.github.hlg212.fcf.constants.TaskConstants;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.FeignClientContextUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author wuwei
 */
@JobHandler(value="JobExecutor")
@Component
public class JobExecutor extends IJobHandler {

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("XXL-JOB, Hello World.");

		if(StringUtils.isBlank(param)){
			ExceptionHelper.throwBusinessException("参数param为空");
		}
		String[] params = param.split(";");
		if(params.length < 2){
			ExceptionHelper.throwBusinessException("参数param格式不对，格式为：系统名;bean名;String类型参数");
		}
		TaskApi taskApi = FeignClientContextUtils.getBean(TaskApi.class, params[0]);
		String result = "";
		if(params.length == 2){
			result = taskApi.execute(params[1],"");
		}
		if(params.length == 3){
			result = taskApi.execute(params[1],params[2]);
		}

		if(TaskConstants.SUCCESS.equals(result)){
			return SUCCESS;
		}
		if(TaskConstants.FAIL.equals(result)){
			return FAIL;
		}

		return SUCCESS;
	}

}
