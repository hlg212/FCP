package io.github.hlg212.basic.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;

/** 
 * 机构树 
 * 
 * @author huanglg
 * @date 2022-03-28
 */
@Table("T_ORG_TREE")
@Data 
public class OrgTree extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="类型")
	private String orgTreeTypeId;

	@Field(description="机构ID")
	private String orgId;

	@Field(description="上级机构ID")
	private String parentOrgId;

	@Field(description="级别")
	private Integer treeLevel;

	@Field(description="定位码")
	private String locationCode;

	@Field(description="排序")
	private Integer sortNum;


}
