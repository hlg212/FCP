package io.github.hlg212.basic.model.bo;

import io.github.hlg212.fcf.model.basic.IClientAuthority;
import lombok.Data;

import java.util.List;

/** 
 * 客户端-权限Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class ClientAuthorityBo implements IClientAuthority {
	
	private static final long serialVersionUID = 1L;

	private String roleId;
	private List<String> authoritys;

}
