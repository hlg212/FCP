package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.ResBo;

import java.util.List;

/** 
 * admin用户资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface AdminUserResService {

    /**
     * 获取用户某种类型的角色资源
     *
     * @param resType 资源类型
     * @return  资源树
     */
    public List<ResBo> getResTree(String resType);

    public List<ResBo> getAppMenuRes(String appId);

    public List<String> getAppPermissions(String appId);

    public List<String> getAllPermissions();
}
