package io.github.hlg212.basic.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

import java.util.List;

/** 
 * 客户端角色Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class ClientRoleSaveBo {
	
	private static final long serialVersionUID = 1L;

	@Field(description="客户端ID")
	private String clientId;

	@Field(description="角色id数组")
	List<String> roleIds;

	@Field(description="删除的角色id数组")
	List<String> delRoleIds;

}
