package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.qco.ResQco;
import io.github.hlg212.fcf.service.impl.CurdieServiceImpl;

import java.util.List;

/** 
 * 资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface AuthService {


    /**
     * 获取所有的需要授权的资源;
     *
     * @return 资源集合
     */
    public List<ResBo> getAllAuthRes();

    /**
     * 获取用户的应用列表
     *
     * @param userId 用户id
     * @param type 应用类型
     * @return 应用列表
     */
    public List<AppBo> getAppsByUserId(String userId, String type);

    /**
     * 获取用户的菜单资源集合
     *
     * @param appCode 应用编码
     * @param userId 用户id
     * @return 资源集合
     */
    public List<ResBo> getMenuTree(String appCode, String userId);

    /**
     * 获取应用下的权限代码集合
     *
     * @param appCode 应用编码
     * @param userId 用户id
     * @return 权限代码集合
     */
    public List<String> getAppPermissions(String appCode, String userId);

    /**
     * 获取用户的所有权限代码集合
     *
     * @param userId 用户id
     * @return 权限代码集合
     */
    public List<String> getAllPermissions(String userId);



}
