package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.enums.RoleTypeEnum;
import io.github.hlg212.basic.model.qco.AppQco;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.basic.service.*;
import io.github.hlg212.fcf.model.QueryParam;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.TreeHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限模块实现
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppService appService;

    private final ResService resService;

    private final UserService userService;

    private final UserRoleService userRoleService;

    private final AppRoleService appRoleService;

    private final UserResService userResService;

    @Override
    public List<ResBo> getAllAuthRes() {
        ResQco qco = new ResQco();
        qco.setPermissionCodeIs(QueryParam.NOT_NULL);
        qco.setUrlIs(QueryParam.NOT_NULL);

        return resService.find(qco);
    }

    @Override
    public List<AppBo> getAppsByUserId(String userId, String type) {
        if (userService.isAdmin(userId)) {
            AppQco qco = new AppQco();
            qco.setType(type);
            return appService.find(qco);
        }
        List<String> roleIds = userRoleService.getRoleIds(userId, RoleTypeEnum.USE.getValue(), null);
        List<AppBo> roleApps = appRoleService.getApps(roleIds);
        List<AppBo> apps = new ArrayList<>(roleApps.size());
        for (AppBo bo : roleApps)
        {
            if(StringUtils.equals(bo.getType(),type))
            {
                apps.add(bo);
            }
        }
       return  apps;
    }

    @Override
    public List<ResBo> getMenuTree(String appCode, String userId) {
       AppBo appBo = appService.getAppByCode(appCode);
       if(Objects.isNull(appBo))
       {
           ExceptionHelper.throwBusinessException(String.format("[%s]应用不存在!",appCode));
       }
       List<ResBo> list = userResService.getAppMenuRes(appBo.getId(),userId,RoleTypeEnum.USE.getValue());
       return TreeHelper.buildTree(list);
    }

    @Override
    public List<String> getAppPermissions(String appCode, String userId) {
        AppBo appBo = appService.getAppByCode(appCode);
        if(Objects.isNull(appBo))
        {
            ExceptionHelper.throwBusinessException(String.format("[%s]应用不存在!",appCode));
        }
        return userResService.getAppPermissions(appBo.getId(),userId,RoleTypeEnum.USE.getValue());
    }

    @Override
    public List<String> getAllPermissions(String userId) {
        return userResService.getAllPermissions(userId,RoleTypeEnum.USE.getValue());
    }
}
