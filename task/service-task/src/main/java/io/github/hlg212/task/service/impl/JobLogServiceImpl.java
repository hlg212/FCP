package io.github.hlg212.task.service.impl;

import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.task.api.client.JobLogCenterApi;
import io.github.hlg212.task.model.bo.JobLogBo;
import io.github.hlg212.task.model.qco.JobLogQco;
import io.github.hlg212.task.service.JobLogService;
import io.github.hlg212.task.xxl.model.XxlJobLog;
import io.github.hlg212.task.xxl.model.XxlPageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    private JobLogCenterApi jobLogCenterApi;

    @Override
    public <E extends JobLogBo> PageInfo<E> findPage(PageQuery<Qco> pageQuery) {
        int pageNum = pageQuery.getPageNum() - 1;
        int start = pageNum * pageQuery.getPageSize();
        int length = pageQuery.getPageSize();
        JobLogQco qco = (JobLogQco) pageQuery.getQco();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        XxlPageResult<XxlJobLog> pageResult = jobLogCenterApi.findPage(start, length, qco.getJobGroup(), qco.getJobId(), qco.getLogStatus(), qco.getFilterTime());
        List<JobLogBo> bos = new ArrayList<JobLogBo>();
        List<XxlJobLog> lists = pageResult.getData();
        if (lists != null && lists.size() > 0) {
            for (XxlJobLog m : lists) {
                JobLogBo bo = new JobLogBo();
                try {
                    PropertyUtils.copyProperties(bo, m);
                } catch (Exception e) {
                    log.error("复制数据出现错误!", e);
                }
                bos.add(bo);
            }
        }
        PageInfo<E> info = new PageInfo(bos);
        info.setTotal(pageResult.getRecordsTotal());
        return info;
    }

    @Override
    public String clear(int jobGroup, int jobId, int type) {

        return jobLogCenterApi.clearLog(jobGroup, jobId, type);
    }


}
