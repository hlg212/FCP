package io.github.hlg212.basic.dao;

import io.github.hlg212.basic.model.bo.ResBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 
 * 用户-资源Dao
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface UserResDao {

    /**
     * 查询用户某个用下的
     *
     * @param appId 应用id
     * @param userId 用户id
     * @param roleType 角色类型
     * @return 资源列表
     */
    public List<ResBo> getAppMenuRes(@Param("appId")String appId, @Param("userId")String userId, @Param("roleType") String roleType);

    /**
     * 查询某个用户在应用下的权限列表
     *
     * @param appId 应用id
     * @param userId 用户id
     * @param roleType 角色类型
     * @return 权限代码列表
     */
    public List<String> getAppPermissions(@Param("appId")String appId, @Param("userId")String userId,@Param("roleType")String roleType);

    /**
     * 获取用户所有的权限代码
     *
     * @param userId 应用id
     * @param roleType 角色类型
     * @return 权限代码列表
     */
    public List<String> getAllPermissions(@Param("userId")String userId,@Param("roleType")String roleType);

}
