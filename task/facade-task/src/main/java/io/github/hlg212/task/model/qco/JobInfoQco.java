package io.github.hlg212.task.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

@Data
public class JobInfoQco extends Qco {

    @Field( description = "执行器id查询" )
    private int jobGroup;

    @Field( description = "任务描述查询" )
    private String jobDesc;

    @Field( description = "执行handler" )
    private String handler;

    @Field( description = "日期时间" )
    private String filterTime;

    @Field( description = "应用编码查询" )
    private String appCode;
}
