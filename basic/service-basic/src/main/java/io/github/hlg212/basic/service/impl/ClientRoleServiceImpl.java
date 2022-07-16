package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.*;
import io.github.hlg212.basic.model.qco.ClientRoleQco;
import io.github.hlg212.basic.model.qco.RoleQco;
import io.github.hlg212.basic.service.AppRoleService;
import io.github.hlg212.basic.service.ClientRoleService;
import io.github.hlg212.basic.service.RoleResService;
import io.github.hlg212.basic.service.RoleService;
import io.github.hlg212.basic.util.ResCategoryHelper;
import io.github.hlg212.fcf.model.Constants;
import io.github.hlg212.fcf.util.BooleanHelper;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.TreeHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 客户端-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class ClientRoleServiceImpl implements ClientRoleService {

    private final RoleService roleService;

    private final RoleResService roleResService;

    private final AppRoleService appRoleService;

    @Override
    public List<ClientRoleBo> listByClientId(String clientId) {
        if(StringUtils.isEmpty(clientId))
        {
            ExceptionHelper.throwBusinessException("客户端ID不能为空!");
        }
        ClientRoleQco qco = new ClientRoleQco();
        qco.setClientId(clientId);
        return find(qco);
    }

    @Override
    public List<RoleBo> getRoles(String clientId) {
        List<String> roleIds = getRoleIds(clientId);
        if( Objects.isNull(roleIds) || roleIds.isEmpty())
        {
            return null;
        }
        RoleQco qco = new RoleQco();
        qco.setIdIn(roleIds);
        List<RoleBo> roleBos = roleService.find(qco);
        return roleBos;
    }

    private List<String> getRoleIds(String clientId) {
        List<ClientRoleBo> list = listByClientId(clientId);
        List<String> roleIds = new ArrayList<>(list.size());
        CollectionHelper.getPropertyValues(list, "roleId", roleIds);
        return roleIds;
    }

    @Override
    public List<ResBo> getResTree(String clientId,String resCategory) {
        List<String> roleIds = getRoleIds(clientId);
        if(Objects.isNull(roleIds) || roleIds.isEmpty())
        {
            return null;
        }
        List<ResBo> list = roleResService.getRes(roleIds,resCategory);
        List<AppBo> apps = appRoleService.getApps(roleIds);
        List<ResBo> resTree = TreeHelper.buildTree(list);
        List<ResBo> appParentTree = roleResService.buildAppResTree(apps, resTree);

        return appParentTree;
    }

    @Override
    public void saveClientRoles(ClientRoleSaveBo bo) {
        List<String> roleIds = bo.getRoleIds();
        String cid = bo.getClientId();
        List<String> delIds = getDelIds(cid, bo.getDelRoleIds());
        for (String roleId : roleIds) {
            ClientRoleBo cRoleBo = new ClientRoleBo();
            cRoleBo.setClientId(cid);
            cRoleBo.setRoleId(roleId);
            cRoleBo.setIsAutoAuth(Constants.BOOLEAN_Y);
            save(cRoleBo);
        }

        if (!delIds.isEmpty()) {
            deleteById(delIds.toArray());
        }
    }

    private List<String> getDelIds(String clientId, List<String> delRoleIds) {
        if (Objects.isNull(delRoleIds) || delRoleIds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ClientRoleQco qco = new ClientRoleQco();
        qco.setClientId(clientId);
        qco.setRoleIdIn(delRoleIds);
        List<ClientRoleBo> clientRoleBos = find(qco);
        List<String> list = new ArrayList<>();
        CollectionHelper.getPropertyValues(clientRoleBos, "id", list);
        return list;
    }
}
