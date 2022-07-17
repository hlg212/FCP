package io.github.hlg212.task.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

import java.util.Date;


@Data
public class XxlJobLog {
    @Field(description="日志ID")
    private int id;

    @Field(description="执行器id")
    private int jobGroup;
    @Field(description="任务id")
    private int jobId;

    @Field(description="执行地址")
    private String executorAddress;
    @Field(description="执行handler")
    private String executorHandler;
    @Field(description="任务参数")
    private String executorParam;
    @Field(description="分片参数")
    private String executorShardingParam;
    @Field(description="失败重试次数")
    private int executorFailRetryCount;
    @Field(description="调度时间")
    private Date triggerTime;
    @Field(description="调度结果")
    private int triggerCode;
    @Field(description="调度日志")
    private String triggerMsg;
    @Field(description="执行时间")
    private Date handleTime;
    @Field(description="执行状态")
    private int handleCode;
    @Field(description="执行结果")
    private String handleMsg;
}
