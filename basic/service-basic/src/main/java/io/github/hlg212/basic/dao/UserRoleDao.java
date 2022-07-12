package io.github.hlg212.basic.dao;

import io.github.hlg212.basic.model.po.UserRole;
import io.github.hlg212.fcf.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 
 * 用户-角色Dao
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface UserRoleDao extends BaseDao<UserRole> {

    /**
     * 查询角色id列表
     *
     * @param userId 用户id
     * @param roleType 角色类型
     * @param resTypes 资源类型列表
     * @return 角色id
     */
    public List<String> getRoleIds(@Param("userId") String userId, @Param("roleType")String roleType, @Param("resTypes")List<String> resTypes);

}
