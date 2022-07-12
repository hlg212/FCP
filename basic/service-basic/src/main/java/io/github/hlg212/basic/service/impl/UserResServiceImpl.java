package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.dao.UserResDao;
import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.enmu.ResTypeEnum;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.basic.service.*;
import io.github.hlg212.fcf.util.TreeHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.net.QCodec;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class UserResServiceImpl implements UserResService {

    private final UserService userService;

    private final RoleResService roleResService;

    private final AppRoleService appRoleService;

    private final UserRoleService userRoleService;

    private final UserResDao userResDao;

    private final ResService resService;

    private final AdminUserResService adminUserResService;

    @Override
    public List<ResBo> getResTree(String userId, String roleType, String resCategory) {
        if (userService.isAdmin(userId)) {
            return roleResService.getAppResTree(resCategory);
        }

        List<String> roleIds = userRoleService.getRoleIds(userId, roleType, resCategory);
        List<ResBo> list = roleResService.getRes(roleIds);
        List<AppBo> apps = appRoleService.getApps(roleIds);
        List<ResBo> resTree = TreeHelper.buildTree(list);
        List<ResBo> appParentTree = roleResService.buildAppResTree(apps, resTree);

        return appParentTree;
    }

    @Override
    public List<ResBo> getAppMenuRes(String appId, String userId,String roleType) {
        if( userService.isAdmin(userId) )
        {
            return getAppMenuResByAdmin(appId);
        }
        return userResDao.getAppMenuRes(appId, userId,roleType);
    }

    private List<ResBo> getAppMenuResByAdmin(String appId)
    {
        ResQco qco = new ResQco();
        qco.setAppId(appId);
        return resService.find(qco);
    }

    @Override
    public List<String> getAppPermissions(String appId, String userId,String roleType) {
        if( userService.isAdmin(userId) )
        {
            return adminUserResService.getAppPermissions(appId);
        }
        return userResDao.getAppPermissions(appId, userId,roleType);
    }

    @Override
    public List<String> getAllPermissions(String userId,String roleType) {
        if( userService.isAdmin(userId) )
        {
            return adminUserResService.getAllPermissions();
        }
        return userResDao.getAllPermissions(userId,roleType);
    }


}