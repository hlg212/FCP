package io.github.hlg212.basic.service;

import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.ClientRoleBo;

import java.util.List;

/** 
 * 客户端-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface ClientRoleService extends CurdServiceImpl<ClientRoleBo> {

    public List<ClientRoleBo> listByClientId(String clientId);
	
}
