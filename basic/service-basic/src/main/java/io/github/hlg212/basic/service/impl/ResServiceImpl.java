package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.basic.service.ResService;
import io.github.hlg212.fcf.util.TreeHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 * 资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class ResServiceImpl implements ResService {

    @Override
    public List<ResBo> findTree(ResQco qco) {
        List list = find(qco);
        List treeList = TreeHelper.buildTree(list);
        return treeList;
    }

    @Override
    //@Cacheable()
    public List<ResBo> getResTreeByAppId(String appId) {
        ResQco qco = new ResQco();
        qco.setAppId(appId);
        List list = find(qco);
        return TreeHelper.buildTree(list);
    }
}
