package io.github.hlg212.basic.service;

import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.AppRoleBo;
import org.springframework.transaction.annotation.Transactional;

/** 
 * 应用-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface AppRoleService extends CurdServiceImpl<AppRoleBo> {

    @Transactional
    public AppRoleBo save1(AppRoleBo bo) throws Exception;
}
