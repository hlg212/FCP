package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.qco.DictQco;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.DictBo;
import io.github.hlg212.fcf.service.impl.CurdieServiceImpl;

import java.util.List;

/** 
 * 字典Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface DictService extends CurdieServiceImpl<DictBo> {

    public List<DictBo> findTree(DictQco qco);
}
