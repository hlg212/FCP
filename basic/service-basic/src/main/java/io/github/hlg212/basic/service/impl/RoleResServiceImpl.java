package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.dao.RoleResDao;
import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.bo.RoleResBo;
import io.github.hlg212.basic.model.enmu.AppTypeEnum;
import io.github.hlg212.basic.model.enmu.ResCategoryEnum;
import io.github.hlg212.basic.model.enmu.ResTypeEnum;
import io.github.hlg212.basic.model.qco.AppQco;
import io.github.hlg212.basic.model.qco.RoleResQco;
import io.github.hlg212.basic.service.AppService;
import io.github.hlg212.basic.service.ResService;
import io.github.hlg212.basic.service.RoleResService;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.github.hlg212.fcf.util.TreeHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 角色资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class RoleResServiceImpl implements RoleResService {

    private final AppService appService;

    private final ResService resService;

    private final RoleResDao roleResDao;

    @Override
    public void saveRoleRes(String roleId, List<String> resIds, List<String> delResIds) {
        List<String> addIds;
        addIds = resIds;
        List<String> delIds = getDelIds(roleId, delResIds);
        for (String resId : addIds) {
            if (!resId.startsWith("app:")) {
                RoleResBo roleResBo = new RoleResBo();
                roleResBo.setRoleId(roleId);
                roleResBo.setResId(resId);
                save(roleResBo);
            }
        }

        if (!delIds.isEmpty()) {
            deleteById(delIds.toArray());
        }

    }

    private List<String> getDelIds(String roleId, List<String> delResIds) {
        if (Objects.isNull(delResIds) || delResIds.isEmpty())
        {
            return Collections.EMPTY_LIST;
        }
        RoleResQco qco = new RoleResQco();
        qco.setRoleId(roleId);
        qco.setResIdIn(delResIds);
        List<RoleResBo> roleResBos = find(qco);
        List<String> list = new ArrayList<>();
        CollectionHelper.getPropertyValues(roleResBos, "id", list);
        return list;
    }


    @Override
    public List<ResBo> getRoleResTree(String roleId, String resCategory) {
        List<ResBo> resBos = getRoleRes(roleId, resCategory);
        return TreeHelper.buildTree(resBos);
    }

    @Override
    public List<ResBo> getRoleRes(String roleId, String resCategory) {

        return roleResDao.listRoleRes(roleId, convertResTypes(resCategory));
    }

    public List<ResBo> getAppRoleResTree(String roleId, String resType) {
        List<ResBo> treeList = getRoleResTree(roleId, resType);
        List<AppBo> apps = getAppsByResType(resType);
        return buildAppResTree(apps, treeList);
    }

    @Override
    public List<ResBo> buildAppResTree(List<AppBo> apps, List<ResBo> treeList) {
        List<ResBo> result = new ArrayList<>(treeList.size());
        for (AppBo bo : apps) {
            ResBo resBo = new ResBo();
            resBo.setName(bo.getName());
            resBo.setId("app:" + bo.getId());
            result.add(resBo);
        }
        for (ResBo bo : treeList) {
            bo.setParentResId("app:" + bo.getAppId());
        }
        result.addAll(treeList);
        return TreeHelper.buildTree(result);
    }

    @Override
    public List<ResBo> getRes(List<String> roleIds) {
        return roleResDao.listRes(roleIds);
    }

    @Override
    public List<String> listPermissionCodes(String roleId) {

        return roleResDao.listPermissionCodes(roleId);
    }

    @Override
    public List<ResBo> getAppResTree(String resCategory) {
        String appType = convertAppType(resCategory);

        List<AppBo> apps = getAppsByResType(appType);

        List<ResBo> treeList = new ArrayList<>(apps.size());
        for (AppBo bo : apps) {
            treeList.addAll(resService.getResTreeByAppId(bo.getId()));
        }
        return buildAppResTree(apps, treeList);
    }

    private List<AppBo> getAppsByResType(String appType) {
        AppQco qco = new AppQco();
        qco.setType(appType);
        return appService.find(qco);
    }

    private String convertAppType(String resCategory) {
        if ( StringUtils.equalsIgnoreCase(ResCategoryEnum.MENU.getValue(),resCategory)) {
            return AppTypeEnum.WEB.getValue();
        }
        return AppTypeEnum.SERVICE.getValue();
    }

    private List<String> convertResTypes(String resCategory) {
        List<String> types = new ArrayList<>();
        if (StringUtils.equalsIgnoreCase(ResCategoryEnum.MENU.getValue(),resCategory)) {
            types.add(ResTypeEnum.MENU.getValue());
            types.add(ResTypeEnum.COMPONENT.getValue());
        } else if ( StringUtils.equalsIgnoreCase(ResCategoryEnum.IFACE.getValue(),resCategory)) {
            types.add(ResTypeEnum.INTERFACE.getValue());
        } else if ( StringUtils.equalsIgnoreCase( ResCategoryEnum.DATA.getValue(),resCategory) ) {
            types.add(ResTypeEnum.DATA.getValue());
        }
        return types;
    }

}
