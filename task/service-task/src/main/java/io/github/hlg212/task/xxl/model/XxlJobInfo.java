package io.github.hlg212.task.xxl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

import java.util.Date;

@Data
public class XxlJobInfo {
    @Field(description="任务ID")
    private int id;

    @Field(description="执行器id")
    private int jobGroup;
    @Field(description="cron表达式")
    private String jobCron;
    @Field(description="任务描述")
    private String jobDesc;

    @Field(description="新增时间")
    private String addTime;

    @Field(description="修改时间")
    private String updateTime;

    @Field(description="负责人")
    private String author;
    @Field(description = "邮件")
    private String alarmEmail;
    @Field(description="路由策略")
    private String executorRouteStrategy;
    @Field(description="执行器任务handler")
    private String executorHandler;
    @Field(description="参数")
    private String executorParam;
    @Field(description="运行模式")
    private String executorBlockStrategy;
    @Field(description="超时时间")
    private int executorTimeout;
    @Field(description="失败重试次数")
    private int executorFailRetryCount;

    @Field(description="限制，只有bean")
    private String glueType;

    private String glueSource;

    private String glueRemark;

    private String glueUpdatetime;

    @Field(description="子任务id")
    private String childJobId;
    @Field(description="状态")
    private String jobStatus;
}
