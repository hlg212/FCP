package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.OrgBo;
import io.github.hlg212.basic.model.bo.OrgTreeBo;
import io.github.hlg212.basic.model.bo.OrgTreeParam;
import io.github.hlg212.basic.model.bo.OrgTreeSaveBo;
import io.github.hlg212.basic.model.qco.OrgTreeQco;
import io.github.hlg212.basic.service.OrgService;
import io.github.hlg212.basic.service.OrgTreeService;
import io.github.hlg212.basic.util.Constants;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.basic.File;
import io.github.hlg212.fcf.util.BooleanHelper;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.TreeHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/** 
 * 机构树Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@Slf4j
public class OrgTreeServiceImpl implements OrgTreeService {

    @Autowired
    private OrgService orgService;

    @Override
    public void saveTree(OrgTreeSaveBo saveBo) {
        List<OrgTreeBo> boTree = saveBo.getOrgTrees();
        delByOrgTreeTypeId( saveBo.getOrgTreeTypeId() );
        buildTree(boTree);
        for ( OrgTreeBo bo :  boTree)
        {
            save(bo);
        }
    }

    private void buildTree(List<OrgTreeBo> orgTreeBos)
    {
        Map<String,OrgTreeBo> orgTreMap = new HashMap<>();
        CollectionHelper.getPropertyMap(orgTreeBos,"orgId",orgTreMap);
        Map<String,Integer> sortNumMap = new HashMap<>();

        for ( OrgTreeBo bo :  orgTreeBos)
        {
            buildTree(orgTreMap,sortNumMap,bo);
        }
    }
    private void buildTree(Map<String,OrgTreeBo> cacheMap,Map<String,Integer> sortNumMap,OrgTreeBo bo)
    {
        OrgTreeBo parent =  cacheMap.get(bo.getPid());
        String locationCode = null;
        Integer sortNum = sortNumMap.get(bo.getPid());
        Integer lv = 0;
        if( parent != null )
        {
            if( StringUtils.isEmpty(parent.getLocationCode())
                    || Objects.isNull(parent.getTreeLevel() )
                    || Objects.isNull( parent.getSortNum() ) )
            {
                buildTree(cacheMap,sortNumMap,parent);
            }
            locationCode = parent.getLocationCode();
            lv  =  parent.getTreeLevel();
        }

        if( StringUtils.isEmpty(locationCode))
        {
            locationCode = bo.getOrgId();
        }
        else
        {
            locationCode += Constants.OrgType.LOCATIONCODE_SPLIT + bo.getOrgId();
        }
        lv ++;
        if( Objects.isNull( sortNum ) )
        {
            sortNum = 0;
        }
        else
        {
            sortNum ++;
        }
        sortNumMap.put(bo.getPid(),sortNum);
        bo.setLocationCode(locationCode);
        bo.setSortNum(sortNum);
        bo.setTreeLevel(lv);
    }

    private void delByOrgTreeTypeId(String orgTreeTypeId)
    {
        OrgTreeQco qco = new OrgTreeQco();
        qco.setOrgTreeTypeId(orgTreeTypeId);
        List<OrgTreeBo> orgTreeBos = find(qco);
        List<String> ids = new ArrayList<>(orgTreeBos.size());
        CollectionHelper.getPropertyValues(orgTreeBos,"id",ids);
        if( !ids.isEmpty() ) {
            deleteById(ids.toArray());
        }
    }

    @Override
    public List<OrgTreeBo> findTree(OrgTreeQco qco) {
        List<OrgTreeBo>  bos = find(qco);
        for( OrgTreeBo bo : bos )
        {
            bo.setOrg(orgService.getById(bo.getOrgId()));
        }
        try {
            return TreeHelper.buildTree(bos,"orgId","pid","children");
        } catch (Exception e) {
            ExceptionHelper.throwServerException(e);
        }
        return null;
    }

    @Override
    public List<OrgTreeBo> getChildList(OrgTreeParam param) {
        OrgTreeBo currOrg = getOrgTreeBo(param.getOrgTreeTypeCode(),param.getOrgId());
        if(Objects.isNull(currOrg))
        {
            log.warn("机构树[{}]中不存在机构[{}]不存在!",param.getOrgTreeTypeCode(),param.getOrgId());
            return Collections.emptyList();
        }
        Integer depth = param.getDepth();
        if (Objects.isNull(depth)) {
            depth = 99;
        }
        depth += currOrg.getTreeLevel();
        OrgTreeQco qco = new OrgTreeQco();
        qco.setOrgTreeTypeId(param.getOrgTreeTypeCode());
        qco.setTreeLevelLtEq(depth);
        qco.setLocationCodeRtLike(currOrg.getLocationCode() + Constants.OrgType.LOCATIONCODE_SPLIT);

        List<OrgTreeBo> orgTreeBos = find(qco);
        if( BooleanHelper.to(param.getIsSelf()) )
        {
            ArrayList<OrgTreeBo> list = new ArrayList<>(orgTreeBos.size()+1);
            list.add(currOrg);
            list.addAll(orgTreeBos);
            return list;
        }

        return orgTreeBos;
    }

    @Override
    public List<OrgTreeBo> getChildTree(OrgTreeParam param) {
        List<OrgTreeBo> list = getChildList(param);
        return TreeHelper.buildTree(list);
    }

    @Override
    public List<OrgTreeBo> getParentList(OrgTreeParam param) {
        OrgTreeBo currOrg = getOrgTreeBo(param.getOrgTreeTypeCode(),param.getOrgId());
        if(Objects.isNull(currOrg))
        {
            log.warn("机构树[{}]中不存在机构[{}]不存在!",param.getOrgTreeTypeCode(),param.getOrgId());
            return Collections.emptyList();
        }
        Integer depth = param.getDepth();
        if (Objects.isNull(depth)) {
            depth = -99;
        }
        // 往上走
        depth = currOrg.getTreeLevel() - depth;

        String locationCode = currOrg.getLocationCode();
        locationCode = locationCode.split(Constants.OrgType.LOCATIONCODE_SPLIT)[0];

        OrgTreeQco qco = new OrgTreeQco();
        qco.setOrgTreeTypeId(param.getOrgTreeTypeCode());
        qco.setLocationCodeRtLike(locationCode + Constants.OrgType.LOCATIONCODE_SPLIT );
        // 大于等于深度级别
        // 当前树的等级为 5, 上两级的数据就是 >=3;
        // 最终获得是是 3,4 级的数据；
        qco.setTreeLevelGtEq(depth);
        // 小于 当前级别；
        qco.setTreeLevelLt(currOrg.getTreeLevel());
        List<OrgTreeBo> orgTreeBos = find(qco);
        if( BooleanHelper.to(param.getIsSelf()) )
        {
            orgTreeBos.add(currOrg);
        }
        return orgTreeBos;
    }

    private OrgTreeBo getOrgTreeBo(String orgTreeTypeCode,String orgId)
    {
        OrgTreeQco qco = new OrgTreeQco();
        qco.setOrgTreeTypeId(orgTreeTypeCode);
        qco.setOrgId(orgId);
        OrgTreeBo currOrg = get(qco);
        return currOrg;
    }

    @Override
    public List<OrgTreeBo> getParentTree(OrgTreeParam param) {
        List<OrgTreeBo> list = getChildList(param);
        return TreeHelper.buildTree(list,"orgId","pid","children");
    }

    @Override
    public OrgTreeBo getParent(String orgTreeTypeCode, String orgId) {
        OrgTreeBo currOrg = getOrgTreeBo(orgTreeTypeCode,orgId);
        return  getOrgTreeBo(orgTreeTypeCode,currOrg.getParentOrgId());
    }

    @Override
    public File export(List datas) {
        Iterator iter = datas.iterator();
        while( iter.hasNext() )
        {
            OrgTreeBo bo  = (OrgTreeBo)iter.next();
            bo.setOrg(orgService.getById(bo.getOrgId()));
        }
        return OrgTreeService.super.export(datas);
    }

    /**
     * 机构不存在时，需要先创建机构
     *
     * @param datas
     */
    @Override
    public void importSave(Collection<OrgTreeBo> datas) {
        OrgTreeSaveBo saveBo = new OrgTreeSaveBo();
        List<OrgTreeBo> bos = new ArrayList<>(datas.size());
        bos.addAll(datas);
        if( !bos.isEmpty() )
        {
            saveBo.setOrgTreeTypeId(bos.get(0).getOrgTreeTypeId());
        }
        Iterator iter = datas.iterator();
        while( iter.hasNext() )
        {
            OrgTreeBo bo = (OrgTreeBo)iter.next();
            OrgBo org = bo.getOrg();
            if( StringUtils.isEmpty(org.getId())) {
               orgService.save(org);
            }
            else{
                orgService.update(org);
            }
            bo.setOrgId(org.getId());
        }
        if( bos.isEmpty() )
        {
            ExceptionHelper.throwBusinessException("请不要导入空文件!");
        }
        saveBo.setOrgTrees(bos);
        saveTree(saveBo);
    }


}
