package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * 用户-角色Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class UserRoleQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	private String userId;

	private String roleId;

}
