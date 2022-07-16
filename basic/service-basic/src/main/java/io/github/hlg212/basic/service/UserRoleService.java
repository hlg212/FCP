package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.bo.RoleBo;
import io.github.hlg212.basic.model.bo.UserRoleSaveBo;
import io.github.hlg212.basic.model.po.Res;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.UserRoleBo;

import java.util.List;

/** 
 * 用户-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface UserRoleService extends CurdServiceImpl<UserRoleBo> {

    /**
     * 获取用户的角色列表；
     *
     * @param userId
     * @param roleType
     * @return
     */
    List<String> getRoleIds(String userId, String roleType,String resCategory);

    /**
     * 保存用户角色
     *
     * @param bo bo
     */
    void saveRoles(UserRoleSaveBo bo);


    /**
     * 获取用户的角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<RoleBo> getRoles(String userId);
}
