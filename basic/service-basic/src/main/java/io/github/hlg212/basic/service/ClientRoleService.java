package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.*;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import java.util.List;

/** 
 * 客户端-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface ClientRoleService extends CurdServiceImpl<ClientRoleBo> {


    List<ClientRoleBo> listByClientId(String clientId);

    /**
     * 获取客户端的角色列表
     * @param clientId 客户端id
     * @return 角色列表
     */
    List<RoleBo> getRoles(String clientId);

    /**
     * 获得客户端分配的资源树
     * @param clientId 客户端id
     * @return 资源树
     */
    public List<ResBo> getResTree(String clientId,String resCategory);


    /**
     * 保存客户端角色
     *
     * @param bo bo
     */
    public void saveClientRoles(ClientRoleSaveBo bo);
}
