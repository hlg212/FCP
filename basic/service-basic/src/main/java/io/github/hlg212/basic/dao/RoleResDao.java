package io.github.hlg212.basic.dao;

import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.fcf.dao.BaseDao;
import io.github.hlg212.basic.model.po.RoleRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 
 * 角色资源Dao
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface RoleResDao extends BaseDao<RoleRes> {

    public List<ResBo> listRoleRes(@Param("roleId") String roleId,@Param("resTypes") List<String> resTypes);

    public List<ResBo> listRes(@Param("roleIds")List<String> roleIds);

    public List<String> listPermissionCodes(@Param("roleId")String roleId);
}
