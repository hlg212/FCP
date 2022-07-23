package io.github.hlg212.task.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;


@Data
public class JobLogQco extends Qco {

    @Field( description = "执行器id查询" )
    private int jobGroup;

    @Field( description = "任务id查询" )
    private int jobId;

    @Field( description = "日志状态" )
    private int logStatus = -1;

    @Field( description = "日期时间" )
    private String filterTime;

}
