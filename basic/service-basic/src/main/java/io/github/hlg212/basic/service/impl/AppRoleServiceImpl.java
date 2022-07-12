package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.AppRoleBo;
import io.github.hlg212.basic.model.qco.AppRoleQco;
import io.github.hlg212.basic.service.AppRoleService;
import io.github.hlg212.basic.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * 应用-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {

    @Autowired
    private AppService appService;

    @Override
    public List<AppBo> getApps(List<String> roleIds) {
        return listAppByRole(roleIds);
    }

    private List<AppBo> listAppByRole(List<String> roleIds)
    {
        AppRoleQco appRoleQco = new AppRoleQco();
        appRoleQco.setRoleIdIn(roleIds);
        List<AppRoleBo> appRoles = find(appRoleQco);
        Set<String> appIdSet = new HashSet<>();
        List<AppBo> apps = new ArrayList<>();
        for( AppRoleBo appRoleBo : appRoles )
        {
            if( !appIdSet.contains(appRoleBo.getAppId()))
            {
                apps.add(appService.getById(appRoleBo.getAppId()));
            }
        }
        return apps;
    }
}
