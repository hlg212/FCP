package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.RoleRes;
import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.basic.Res;
import lombok.Data;

import java.util.List;

/** 
 * 角色资源Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class RoleResSaveBo {
	
	private static final long serialVersionUID = 1L;
	@Field(description="角色ID")
	private String roleId;

	@Field(description="资源id数组")
	List<String> resIds;

	@Field(description="资源id数组")
	List<String> delResIds;

}
