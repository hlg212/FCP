package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.AppRoleBo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** 
 * 应用-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface AppRoleService extends CurdServiceImpl<AppRoleBo> {

    /**
     * 获取应用列表
     *
     * @param roleIds 角色集合
     * @return
     */
    public List<AppBo> getApps(List<String> roleIds);
}
