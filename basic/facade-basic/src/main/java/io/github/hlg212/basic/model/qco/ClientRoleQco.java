package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

import java.util.List;

/** 
 * 客户端-角色Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class ClientRoleQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	private String clientId;

	private String roleId;

	private List<String> roleIdIn;
}
