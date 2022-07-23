package io.github.hlg212.task.service.impl;

import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.FworkHelper;
import io.github.hlg212.fcf.util.JsonHelper;
import io.github.hlg212.task.api.client.JobCenterApi;
import io.github.hlg212.task.model.bo.JobInfoBo;
import io.github.hlg212.task.model.qco.JobInfoQco;
import io.github.hlg212.task.service.JobInfoService;
import io.github.hlg212.task.xxl.model.XxlJobInfo;
import io.github.hlg212.task.xxl.model.XxlPageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobCenterApi jobCenterApi;


    @Override
    public <E extends JobInfoBo> PageInfo<E> findPage(PageQuery<Qco> pageQuery) {
        int start = 0;
        int length = 10000;
        JobInfoQco qco = (JobInfoQco) pageQuery.getQco();
        XxlPageResult<XxlJobInfo> pageResult = jobCenterApi.findPage(start, length, qco.getJobGroup(),-1, qco.getJobDesc(), qco.getHandler(), qco.getFilterTime());
        List<JobInfoBo> bos = new ArrayList<>();

        List<XxlJobInfo> lists = pageResult.getData();
        if (lists != null && lists.size() > 0) {
            for (XxlJobInfo m : lists) {
                if (m.getExecutorParam().startsWith(qco.getAppCode())) {
                    JobInfoBo bo = new JobInfoBo();
                    try {
                        PropertyUtils.copyProperties(bo, m);
                    } catch (Exception e) {
                        log.error("复制数据出现错误!", e);
                    }

                    String[] executorParam = m.getExecutorParam().split(";");
                    bo.setAppCode(executorParam[0]);
                    bo.setJobBean(executorParam[1]);
                    if (executorParam.length == 3) {
                        bo.setParam(executorParam[2]);
                    } else {
                        bo.setParam(null);
                    }
                    bos.add(bo);
                }
            }
        }

        List<JobInfoBo> page = new ArrayList<JobInfoBo>();
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < bos.size() - currIdx; i++) {
            JobInfoBo bo = bos.get(currIdx + i);
            page.add(bo);
        }

        PageInfo<E> info = new PageInfo(page);
        info.setTotal(bos.size());

        return info;
    }

    @Override
    public JobInfoBo save(JobInfoBo bo) {
        IUser user = FworkHelper.getUser();
        if (user != null) {
            bo.setAuthor(user.getName());
        }

        MultiValueMap<String, Object> map = changeMap(bo);
        String str = jobCenterApi.add(map);
        Map m = JsonHelper.parseObject(str, HashMap.class);
        Object id = m.get("content");
        if (id != null) {
            bo.setId(String.valueOf(id));
        }
        return bo;
    }

    @Override
    public JobInfoBo update(JobInfoBo bo) {
        IUser user = FworkHelper.getUser();
        bo.setAuthor(user.getName());

        MultiValueMap<String, Object> map = changeMap(bo);
        jobCenterApi.update(map);
        return bo;
    }

    @Override
    public void deleteById(Object... ids) {
        for (Object id : ids) {
            jobCenterApi.remove(id.toString());
        }

    }

    @Override
    public void start(String id) {
        jobCenterApi.start(id);
    }

    @Override
    public void stop(String id) {
        jobCenterApi.stop(id);
    }

    @Override
    public void trigger(String id, String param) {
        jobCenterApi.trigger(id, param);
    }

    private MultiValueMap<String, Object> changeMap(JobInfoBo bo) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        try {
            PropertyUtils.copyProperties(xxlJobInfo, bo);
        } catch (Exception e) {
            log.error("复制数据出现错误!", e);
            ExceptionHelper.throwBusinessException("复制数据出现错误!");
        }
        xxlJobInfo.setExecutorParam(bo.getAppCode() + ";" + bo.getJobBean() + ";" + (bo.getParam() == null ? "" : bo.getParam()));

        Map jsonMap = JsonHelper.toJsonObject(xxlJobInfo,HashMap.class);
        map.putAll(jsonMap);
        return map;
    }

}
