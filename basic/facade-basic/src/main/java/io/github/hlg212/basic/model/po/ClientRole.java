package io.github.hlg212.basic.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;

/** 
 * 客户端-角色 
 * 
 * @author huanglg
 * @date 2022-03-28
 */
@Table("T_CLIENT_ROLE")
@Data 
public class ClientRole extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="客户端ID")
	private String clientId;

	@Field(description="角色ID")
	private String roleId;

	@Field(description="是否自动授权")
	private String isAutoAuth;


}
