package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.ClientRoleBo;
import io.github.hlg212.basic.model.qco.ClientRoleQco;
import io.github.hlg212.basic.service.ClientRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 * 客户端-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class ClientRoleServiceImpl implements ClientRoleService {

    @Override
    public List<ClientRoleBo> listByClientId(String clientId) {
        ClientRoleQco qco = new ClientRoleQco();
        qco.setClientId(clientId);
        return find(qco);
    }
}
