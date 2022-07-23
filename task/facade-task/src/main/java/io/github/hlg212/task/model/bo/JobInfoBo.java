package io.github.hlg212.task.model.bo;


import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.task.model.po.JobInfo;
import lombok.Data;

@Data
public class JobInfoBo extends JobInfo {

    @PkId
    @Field(description="任务ID")
    private String id;

    @Field(description="执行器id")
    private String jobGroup;

    @Field(description="任务描述")
    private String jobDesc;

    @Field(description="路由策略")
    private String executorRouteStrategy;

    @Field(description="cron表达式")
    private String jobCron;

    @Field(description="运行模式")
    private String glueType;

    @Field(description="执行器任务handler")
    private String executorHandler;

    @Field(description="阻塞处理策略")
    private String executorBlockStrategy;

    @Field(description="子任务id")
    private String childJobId;

    @Field(description="超时时间")
    private String executorTimeout;

    @Field(description="失败重试次数")
    private String executorFailRetryCount;

    @Field(description="负责人")
    private String author;

    @Field(description = "邮件")
    private String alarmEmail;

    @Field(description="系统名")
    private String appCode;

    @Field(description="类名")
    private String jobBean;

    @Field(description="参数")
    private String param;

    @Field(description="状态")
    private String jobStatus;
}
