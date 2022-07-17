package io.github.hlg212.task.service.impl;

import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.FworkHelper;
import io.github.hlg212.fcf.util.JsonHelper;
import io.github.hlg212.task.api.client.JobCenterApi;
import io.github.hlg212.task.model.bo.JobInfoBo;
import io.github.hlg212.task.model.qco.JobInfoQco;
import io.github.hlg212.task.service.JobInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务信息Service实现类
 *
 * @author wuwei
 * @date 2019年2月27日
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobCenterApi jobCenterApi;


    @Override
    public <E extends JobInfoBo> PageInfo<E> findPage(PageQuery<Qco> pageQuery){
        int start = 0;
        int length = 10000;
        JobInfoQco qco = (JobInfoQco) pageQuery.getQco();
        Map<String, Object> map = jobCenterApi.findPage(start, length, qco.getZxqid(), qco.getRwms(), qco.getHandler(), qco.getFilterTime());
        List<JobInfoBo> bos = new ArrayList<>();

        List<Map> lists = (List<Map>) map.get("data");
        if(lists != null && lists.size() > 0){
            for(Map m : lists){
                if(m.get("executorParam").toString().startsWith(qco.getYybm())){
                    JobInfoBo bo = new JobInfoBo();
                    bo.setId(m.get("id") + "");
                    bo.setZxqid(m.get("jobGroup") + "");
                    bo.setRwms(m.get("jobDesc") + "");
                    bo.setLycl(m.get("executorRouteStrategy") + "");
                    bo.setCron(m.get("jobCron") + "");
                    bo.setYxms(m.get("glueType") + "");
                    bo.setHandler(m.get("executorHandler") + "");
                    bo.setZsclcl(m.get("executorBlockStrategy") + "");
                    bo.setZrwid(m.get("childJobId") + "");
                    bo.setCssj(m.get("executorTimeout") + "");
                    bo.setSbcscs(m.get("executorFailRetryCount") + "");
                    bo.setCjr(m.get("author") + "");
                    bo.setEmail(m.get("alarmEmail") + "");
                    String[] executorParam = (m.get("executorParam") + "").split(";");
                    bo.setXtm(executorParam[0]);
                    bo.setLm(executorParam[1]);
                    if(executorParam.length == 3){
                        bo.setParam(executorParam[2]);
                    }else{
                        bo.setParam(null);
                    }
                    bo.setStatus(m.get("jobStatus") + "");

                    bos.add(bo);
                }
            }
        }

        List<JobInfoBo> page = new ArrayList<JobInfoBo>();
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        int currIdx = (pageNum > 1 ? (pageNum -1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < bos.size() - currIdx; i++) {
            JobInfoBo bo = bos.get(currIdx + i);
            page.add(bo);
        }

        PageInfo<E> info = new PageInfo(page);
        info.setTotal(bos.size());

        return info;
    }

    @Override
    public JobInfoBo save(JobInfoBo dsrwBo){
        IUser user = FworkHelper.getUser();
        if(user != null){
            dsrwBo.setCjr(user.getName());
        }

        MultiValueMap<String, Object> map = changeMap(dsrwBo);
        String str = jobCenterApi.add(map);
        Map m = JsonHelper.parseObject(str,HashMap.class);
        Object id = m.get("content");
        if( id != null ) {
            dsrwBo.setId( String.valueOf( id ) );
        }
        return dsrwBo;

//        String url = addressUrl + "/add";
//        Map<String, Object> map = changeMap(rwxxBo);
//        try {
//            String s = HttpRequestUtils.readContentFromPost(url, map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rwxxBo;
    }

    @Override
    public JobInfoBo update(JobInfoBo rwxxBo){
        IUser user = FworkHelper.getUser();
        rwxxBo.setCjr(user.getName());

        MultiValueMap<String, Object> map = changeMap(rwxxBo);
        jobCenterApi.update(map);
        return rwxxBo;

//        String url = addressUrl + "/update";
//        Map<String, Object> map = changeMap2(rwxxBo);
//        try {
//            String s = HttpRequestUtils.readContentFromPost(url, map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rwxxBo;
    }

    @Override
    public void deleteById(Object... ids) {
        for(Object id : ids){
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

    private MultiValueMap<String, Object> changeMap(JobInfoBo rwxxBo){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if(rwxxBo.getId() != null){
            map.add("id", rwxxBo.getId());
        }
        map.add("jobGroup",rwxxBo.getZxqid() == null ? "1" : rwxxBo.getZxqid() + "");
        map.add("jobCron",rwxxBo.getCron());
        map.add("jobDesc",rwxxBo.getRwms());
        map.add("author",rwxxBo.getCjr());
        map.add("alarmEmail",rwxxBo.getEmail());
        map.add("executorRouteStrategy",rwxxBo.getLycl());
        map.add("executorHandler",rwxxBo.getHandler());
        map.add("executorParam",rwxxBo.getXtm() + ";" + rwxxBo.getLm() + ";" + (rwxxBo.getParam() == null ? "" : rwxxBo.getParam()));
        map.add("executorBlockStrategy",rwxxBo.getZsclcl());
        if(StringUtils.isNotBlank(rwxxBo.getCssj())){
            map.add("executorTimeout",rwxxBo.getCssj());
        }
        if(StringUtils.isNotBlank(rwxxBo.getSbcscs())){
            map.add("executorFailRetryCount",rwxxBo.getSbcscs());
        }
        map.add("glueType","BEAN");
        map.add("glueSource", "");
        map.add("glueRemark","");
        map.add("childJobId",rwxxBo.getZrwid() == null ? "" : rwxxBo.getZrwid() + "");
        return map;
    }

}
