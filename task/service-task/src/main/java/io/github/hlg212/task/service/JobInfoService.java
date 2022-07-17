package io.github.hlg212.task.service;

import io.github.hlg212.fcf.service.impl.CurdServiceImpl;
import io.github.hlg212.task.model.bo.JobInfoBo;

/**
 * 任务信息Service
 *
 * @author wuwei
 * @date 2019年2月27日
 */
public interface JobInfoService extends CurdServiceImpl<JobInfoBo> {
    /**
     * 开始任务
     * @param id
     */
    public void start(String id);

    /**
     * 结束任务
     * @param id
     */
    public void stop(String id);

    /**
     * 开始任务
     * @param id
     * @param param
     */
    public void trigger(String id, String param);
}
