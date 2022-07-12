package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.basic.model.bo.RoleResBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;

import java.util.List;

/** 
 * 角色资源Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface RoleResService extends CurdServiceImpl<RoleResBo> {

    /**
     * 保存角色与资源
     *
     * @param roleId 角色id;
     * @param resIds 保存的资源id集合
     * @param delResIds 删除的资源id集合
     */
    public void saveRoleRes(String roleId,List<String> resIds,List<String> delResIds);

    /**
     * 获取角色资源树
     *
     * @param roleId 角色id
     * @param resCategory 资源类型
     * @return 资源树
     */
    public List<ResBo> getRoleResTree(String roleId,String resCategory);

    /**
     * 获取角色资源列表
     *
     * @param roleId 角色id
     * @param resCategory 资源类型
     * @return 资源列表
     */
    public List<ResBo> getRoleRes(String roleId,String resCategory);

    /**
     * 将app作为顶级树节点
     *
     * @param roleId 角色id
     * @param resCategory 资源大类型； 菜单(组件)、接口、数据
     * @return 资源树
     */
    public List<ResBo> getAppRoleResTree(String roleId,String resCategory);

    /**
     * 将app作为顶级树节点
     *
     * @param resCategory 资源大类型； 菜单(组件)、接口、数据
     * @return 资源树
     */
    public List<ResBo> getAppResTree(String resCategory);


    /**
     * 构建一个资源树
     * 顶级节点为 app 的资源树；
     * @param apps 应用数组
     * @param treeList 资源树数组
     * @return 资源树
     */
    public List<ResBo> buildAppResTree(List<AppBo> apps, List<ResBo> treeList);

    public List<ResBo> getRes(List<String> roleIds);

    public List<String> listPermissionCodes(String roleId);
}
