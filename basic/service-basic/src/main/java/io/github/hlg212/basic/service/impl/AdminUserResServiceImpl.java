package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.basic.service.AdminUserResService;
import io.github.hlg212.basic.service.ResService;
import io.github.hlg212.basic.service.RoleResService;
import io.github.hlg212.fcf.model.QueryParam;
import io.github.hlg212.fcf.util.CollectionHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户-资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class AdminUserResServiceImpl implements AdminUserResService {

    private final RoleResService roleResService;

    private final ResService resService;

    @Override
    public List<ResBo> getResTree(String resType) {
        return roleResService.getAppResTree(resType);
    }

    @Override
    public List<ResBo> getAppMenuRes(String appId) {
        ResQco qco = new ResQco();
        qco.setAppId(appId);
        return resService.find(qco);
    }
    @Override
    public List<String> getAppPermissions(String appId) {
        ResQco qco = new ResQco();
        qco.setAppId(appId);
        List<ResBo> list =  resService.find(qco);

        return getPermissions(list);
    }

    @Override
    public List<String> getAllPermissions() {
        ResQco qco = new ResQco();
        qco.setPermissionCodeIs(QueryParam.NOT_NULL);
        List<ResBo> list =  resService.find(qco);
        return getPermissions(list);
    }

    private List<String> getPermissions(List<ResBo> resBos)
    {
        Set<String> permissionSet = new HashSet<>();
        CollectionHelper.getPropertyValues(resBos,"permissionCode",permissionSet);
        List<String> permissions = new ArrayList<>(permissionSet.size());
        permissions.addAll(permissionSet);
        return permissions;
    }


}
