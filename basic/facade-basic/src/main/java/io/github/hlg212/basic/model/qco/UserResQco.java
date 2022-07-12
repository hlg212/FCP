package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

/** 
 * 用户Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class UserResQco extends Qco {
	private static final long serialVersionUID = 1L;
	@Field(description = "应用id")
	private String appId;

	@Field(description = "用户id")
	private String userId;

	@Field(description = "角色类型")
	private String roleType;

}
