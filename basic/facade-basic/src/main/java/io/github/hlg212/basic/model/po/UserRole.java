package io.github.hlg212.basic.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;

/** 
 * 用户-角色 
 * 
 * @author huanglg
 * @date 2022-03-28
 */
@Table("T_USER_ROLE")
@Data 
public class UserRole extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="用户ID")
	private String userId;

	@Field(description="角色ID")
	private String roleId;


}
