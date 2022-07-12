package io.github.hlg212.basic.service.impl;

import io.github.hlg212.basic.dao.UserRoleDao;
import io.github.hlg212.basic.model.bo.UserRoleBo;
import io.github.hlg212.basic.model.bo.UserRoleSaveBo;
import io.github.hlg212.basic.model.enmu.ResCategoryEnum;
import io.github.hlg212.basic.model.enmu.ResTypeEnum;
import io.github.hlg212.basic.service.RoleService;
import io.github.hlg212.basic.service.UserRoleService;
import io.github.hlg212.basic.service.UserService;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.PageQuery;
import io.github.hlg212.fcf.model.Qco;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void saveUserRoles(UserRoleSaveBo bo) {

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
