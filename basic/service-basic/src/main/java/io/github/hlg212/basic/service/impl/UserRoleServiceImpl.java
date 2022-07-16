package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.dao.UserRoleDao;
import io.github.hlg212.basic.model.bo.*;
import io.github.hlg212.basic.model.enmu.ResCategoryEnum;
import io.github.hlg212.basic.model.enmu.ResTypeEnum;
import io.github.hlg212.basic.model.qco.ClientRoleQco;
import io.github.hlg212.basic.model.qco.RoleQco;
import io.github.hlg212.basic.model.qco.RoleResQco;
import io.github.hlg212.basic.model.qco.UserRoleQco;
import io.github.hlg212.basic.service.RoleService;
import io.github.hlg212.basic.service.UserRoleService;
import io.github.hlg212.basic.service.UserService;
import io.github.hlg212.basic.util.ResCategoryHelper;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.github.hlg212.fcf.util.ExceptionHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 用户-角色Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleDao userRoleDao;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public List<String> getRoleIds(String userId, String roleType, String resCategory) {
        List<String> resTypes = convertResTypes(resCategory);
        return userRoleDao.getRoleIds(userId, roleType, resTypes);
    }

    @Override
    public void saveRoles(UserRoleSaveBo bo) {
        List<String> roleIds = bo.getRoleIds();
        String uid = bo.getUserId();
        List<String> delIds = getDelIds( uid, bo.getDelRoleIds());
        for (String roleId : roleIds) {
            UserRoleBo userRoleBo = new UserRoleBo();
            userRoleBo.setUserId(uid);
            userRoleBo.setRoleId(roleId);
            save(userRoleBo);
        }

        if (!delIds.isEmpty()) {
            deleteById(delIds.toArray());
        }
    }

    @Override
    public List<RoleBo> getRoles(String userId) {
        List<String> roleIds = getRoleIds(userId);
        if(Objects.isNull(roleIds) || roleIds.isEmpty())
        {
            return null;
        }
        RoleQco qco = new RoleQco();
        qco.setIdIn(roleIds);
        List<RoleBo> roleBos = roleService.find(qco);
        return roleBos;
    }

    private List<String> getRoleIds(String userId) {
        List<UserRoleBo> list = listByUserId(userId);
        List<String> roleIds = new ArrayList<>(list.size());
        CollectionHelper.getPropertyValues(list, "roleId", roleIds);
        return roleIds;
    }

    private List<UserRoleBo> listByUserId(String userId) {
        if(StringUtils.isEmpty(userId))
        {
            ExceptionHelper.throwBusinessException("用户ID不能为空!");
        }
        UserRoleQco qco = new UserRoleQco();
        qco.setUserId(userId);
        return find(qco);
    }

    private List<String> getDelIds(String userId, List<String> delRoleIds) {
        if (Objects.isNull(delRoleIds) || delRoleIds.isEmpty())
        {
            return Collections.EMPTY_LIST;
        }
        UserRoleQco qco = new UserRoleQco();
        qco.setUserId(userId);
        qco.setRoleIdIn(delRoleIds);
        List<UserRoleBo> bos = find(qco);
        List<String> list = new ArrayList<>();
        CollectionHelper.getPropertyValues(bos, "id", list);
        return list;
    }


    @Override
    public <E extends UserRoleBo> PageInfo<E> findPage(PageQuery<Qco> pageQuery) {
        PageInfo<E> pageInfo = UserRoleService.super.findPage(pageQuery);
        for( UserRoleBo bo : pageInfo.getList() )
        {
            bo.setRole(roleService.getById(bo.getRoleId()));
            bo.setUser(userService.getById(bo.getUserId()));
        }
        return pageInfo;
    }

    private List<String> convertResTypes(String resCategory) {
       return ResCategoryHelper.convertResTypes(resCategory);
    }
}
