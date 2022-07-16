package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.fcf.service.impl.CurdieServiceImpl;

import java.util.List;

/** 
 * 资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface ResService extends CurdieServiceImpl<ResBo> {

    public List<ResBo> findTree(ResQco qco);

    public List<ResBo> getResTreeByAppId(String appId,String resCategory);


}
