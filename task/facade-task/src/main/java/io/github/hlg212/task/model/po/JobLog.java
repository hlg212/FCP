package io.github.hlg212.task.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import lombok.Data;

import java.util.Date;

/** 
 * 任务日志表
 * 
 * @author wuwei
 * @date 2019年2月27日
 */
@Data
public class JobLog extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="日志ID")
	private String id;

	//jobGroup
	@Field(description="执行器id")
	private String zxqid;
	//jobId
	@Field(description="任务id")
	private String rwid;

	// execute info
	//executorAddress;
	@Field(description="执行地址")
	private String zxdz;
	//executorHandler;
	@Field(description="执行handler")
	private String handler;
	//executorParam;
	@Field(description="任务参数")
	private String rwcs;
	//executorShardingParam;
	@Field(description="分片参数")
	private String fpcs;
	//executorFailRetryCount;
	@Field(description="失败重试次数")
	private String sbcscs;

	// trigger info
	//triggerTime;
	@Field(description="调度时间")
	private String ddsj;
	//triggerCode;
	@Field(description="调度结果")
	private String ddjg;
	//triggerMsg;
	@Field(description="调度日志")
	private String ddrz;

	// handle info
	//handleTime;
	@Field(description="执行时间")
	private String zxsj;
	//handleCode;
	@Field(description="执行状态")
	private String zxzt;
	//handleMsg;
	@Field(description="执行结果")
	private String zxjg;
}
