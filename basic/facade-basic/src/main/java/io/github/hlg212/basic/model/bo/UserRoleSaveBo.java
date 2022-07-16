package io.github.hlg212.basic.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

import java.util.List;

/** 
 * 用户角色Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class UserRoleSaveBo {
	
	private static final long serialVersionUID = 1L;

	@Field(description="用户ID")
	private String userId;

	@Field(description="角色id数组")
	List<String> roleIds;

	@Field(description="删除的角色id数组")
	List<String> delRoleIds;

}
