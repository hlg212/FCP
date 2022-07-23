package io.github.hlg212.task.service;

import io.github.hlg212.fcf.service.impl.CurdServiceImpl;
import io.github.hlg212.task.model.bo.JobLogBo;

public interface JobLogService extends CurdServiceImpl<JobLogBo> {

    /**
     * 清除
     * @param jobGroup
     * @param jobId
     * @param type
     * @return
     */
    public String clear(int jobGroup, int jobId, int type);

}
