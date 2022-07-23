package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * 机构树Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class OrgTreeQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	@Field(description = "机构树类型")
	private String orgTreeTypeId;

	@Field(description = "机构ID")
	private String orgId;


	@Field(description="名称模糊匹配")
	private String nameLike;

	@Field(description="编码模糊匹配")
	private String codeLike;

	@Field(description="定位码右匹配")
	private String locationCodeRlike;

	@Field(description="级别等于")
	private Integer treeLevel;

	@Field(description="级别大于")
	private Integer treeLevelGt;
	@Field(description="级别大于")
	private Integer treeLevelGtEq;

	@Field(description="级别小于等于")
	private Integer treeLevelLtEq;

	@Field(description="级别小于")
	private Integer treeLevelLt;



}
