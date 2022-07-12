package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.ResBo;

import java.util.List;

/** 
 * 用户资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface UserResService {

    /**
     * 获取用户某种类型的角色资源
     *
     * @param userId 用户id
     * @param roleType 角色类型
     * @param resCategory 资源类型
     * @return  资源树
     */
    public List<ResBo> getResTree(String userId,String roleType,String resCategory);

    public List<ResBo> getAppMenuRes(String appId, String userId,String roleType);

    public List<String> getAppPermissions(String appId, String userId,String roleType);

    public List<String> getAllPermissions(String userId,String roleType);
}
