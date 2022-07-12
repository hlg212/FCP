package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.DictBo;
import io.github.hlg212.basic.model.qco.DictQco;
import io.github.hlg212.basic.service.DictService;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.util.TreeHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 * 字典Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class DictServiceImpl implements DictService {

    @Override
    public List<DictBo> findTree(DictQco qco) {
        List list = find(qco);
        List treeList = TreeHelper.buildTree(list);
        return treeList;
    }
}
