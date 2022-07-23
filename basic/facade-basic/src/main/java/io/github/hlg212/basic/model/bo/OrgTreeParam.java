package io.github.hlg212.basic.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

/** 
 * 机构树Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class OrgTreeParam {
	
	private static final long serialVersionUID = 1L;

	@Field(description = "机构树类型")
	private String orgTreeTypeCode;

	@Field(description = "机构ID")
	private String orgId;

	@Field(description="子机构级别")
	private Integer depth;

	@Field(description="是否包含自己")
	private String isSelf;

}
