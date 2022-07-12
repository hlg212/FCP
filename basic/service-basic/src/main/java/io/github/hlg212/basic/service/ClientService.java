package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.ClientAuthorityBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import io.github.hlg212.basic.model.bo.ClientBo;

import java.util.Collection;

/** 
 * 客户端Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface ClientService extends CurdServiceImpl<ClientBo> {

    public ClientBo getClientAndAuth(String id);

    public Collection<ClientAuthorityBo> getRoleAuthority(String id);

}
