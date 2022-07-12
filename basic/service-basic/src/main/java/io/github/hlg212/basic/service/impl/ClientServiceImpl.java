package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.ClientAuthorityBo;
import io.github.hlg212.basic.model.bo.ClientBo;
import io.github.hlg212.basic.model.bo.ClientRoleBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ClientRoleQco;
import io.github.hlg212.basic.service.ClientRoleService;
import io.github.hlg212.basic.service.ClientService;
import io.github.hlg212.basic.service.RoleResService;
import io.github.hlg212.fcf.util.BooleanHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/** 
 * 客户端Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRoleService clientRoleService;

    private final RoleResService roleResService;

    @Override
    public ClientBo getClientAndAuth(String id) {
        ClientBo clientBo = getById(id);
        List<String> scopes = new ArrayList<>();
        List<String> autoApproveScopes = new ArrayList<>();

        List<ClientRoleBo> clientRoleBos = clientRoleService.listByClientId(id);
        for(ClientRoleBo bo : clientRoleBos )
        {
            scopes.add(bo.getRoleId());
            if( BooleanHelper.to( bo.getIsAutoAuth() ) )
            {
                autoApproveScopes.add(bo.getRoleId());
            }
        }
        if( scopes.isEmpty() )
        {
            scopes.add("client_def_role");
            autoApproveScopes.add("client_def_role");
        }

        clientBo.setScopes( String.join(",",scopes) );
        clientBo.setAutoApproveScopes( String.join(",",autoApproveScopes) );

        return clientBo;
    }

    @Override
    public Collection<ClientAuthorityBo> getRoleAuthority(String id) {
        ArrayList<ClientAuthorityBo> coll = new ArrayList<>();
        List<ClientRoleBo> clientRoleBos = clientRoleService.listByClientId(id);
        for(ClientRoleBo bo : clientRoleBos )
        {
            ClientAuthorityBo clientAuthorityBo = new ClientAuthorityBo();
            coll.add(clientAuthorityBo);
            clientAuthorityBo.setRoleId(bo.getRoleId());
            List<String> permissionCodes = roleResService.listPermissionCodes(bo.getRoleId());
            clientAuthorityBo.setAuthoritys(permissionCodes);
        }
        return coll;
    }

    @Override
    public ClientBo save(ClientBo clientBo) {
        clientBo.setId(clientBo.getAccount());
        return ClientService.super.save(clientBo);
    }
}
