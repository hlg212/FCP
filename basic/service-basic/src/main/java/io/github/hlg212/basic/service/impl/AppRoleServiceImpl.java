package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.AppRoleBo;
import io.github.hlg212.basic.service.AppRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 
 * 应用-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {

    @Override
    @Transactional
    public AppRoleBo save1(AppRoleBo appRoleBo) throws Exception {
        if( 1 == 1)
        throw  new RuntimeException("123");
        return AppRoleService.super.save(appRoleBo);
    }
}
