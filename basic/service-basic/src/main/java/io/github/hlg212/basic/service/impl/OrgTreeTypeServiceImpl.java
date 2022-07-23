package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.OrgTreeTypeBo;
import io.github.hlg212.basic.service.OrgTreeTypeService;
import org.springframework.stereotype.Service;

/** 
 * 机构树类型Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class OrgTreeTypeServiceImpl implements OrgTreeTypeService {

    @Override
    public OrgTreeTypeBo save(OrgTreeTypeBo orgTreeTypeBo) {
        orgTreeTypeBo.setId(orgTreeTypeBo.getCode());
        return OrgTreeTypeService.super.save(orgTreeTypeBo);
    }
}
