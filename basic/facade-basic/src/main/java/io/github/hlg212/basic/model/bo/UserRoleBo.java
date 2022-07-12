package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.UserRole;
import lombok.Data;

/** 
 * 用户-角色Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class UserRoleBo extends UserRole {
	
	private static final long serialVersionUID = 1L;

	private UserBo user;

	private RoleBo role;

}
