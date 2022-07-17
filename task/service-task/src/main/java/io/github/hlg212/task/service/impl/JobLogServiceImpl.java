package io.github.hlg212.task.service.impl;

import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.task.api.client.JobCenterApi;
import io.github.hlg212.task.api.client.JobLogCenterApi;
import io.github.hlg212.task.model.bo.JobLogBo;
import io.github.hlg212.task.model.qco.JobLogQco;
import io.github.hlg212.task.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    private JobLogCenterApi jobLogCenterApi;

    @Override
    public  <E extends JobLogBo> PageInfo<E> findPage(PageQuery<Qco> pageQuery) {
        int  pageNum = pageQuery.getPageNum() - 1;
        int start = pageNum * pageQuery.getPageSize();
        int length = pageQuery.getPageSize();
        JobLogQco qco = (JobLogQco) pageQuery.getQco();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = jobLogCenterApi.findPage(start, length, qco.getZxqid(), qco.getRwid(), qco.getLogStatus(), qco.getFilterTime());
        List<JobLogBo> bos = new ArrayList<JobLogBo>();
        List<Map> lists = (List<Map>) map.get("data");
        if(lists != null && lists.size() > 0){
            for(Map m : lists){
                JobLogBo bo = new JobLogBo();
                bo.setId(m.get("id") + "");
                bo.setZxqid(m.get("jobGroup") + "");
                bo.setRwid(m.get("jobId") + "");
                bo.setZxdz(m.get("executorAddress") + "");
                bo.setHandler(m.get("executorHandler") + "");
                bo.setRwcs(m.get("executorParam") + "");
                bo.setFpcs(m.get("executorShardingParam") + "");
                bo.setSbcscs(m.get("executorFailRetryCount") + "");
                String ddsj = "";
                if(null != m.get("triggerTime")){
                    ddsj = sdf.format(m.get("triggerTime"));
                }
                bo.setDdsj(ddsj);
                bo.setDdjg(m.get("triggerCode") + "");
                bo.setDdrz(m.get("triggerMsg") + "");
                String zxsj = "";
                if(null != m.get("handleTime")){
                    zxsj = sdf.format(m.get("handleTime"));
                }
                bo.setZxsj(zxsj);
                bo.setZxzt(m.get("handleCode") + "");
                bo.setZxjg(m.get("handleMsg") + "");

                bos.add(bo);
            }
        }
        PageInfo<E> info = new PageInfo(bos);
        info.setTotal((int) map.get("recordsTotal"));
        return info;
    }

    @Override
    public String clear(int zxqid, int rwid, int type) {

        return jobLogCenterApi.clearLog(zxqid, rwid, type);
    }


}
