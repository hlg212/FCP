package io.github.hlg212.basic.model.bo;

import io.github.hlg212.fcf.annotation.Field;
import lombok.Data;

import java.util.List;

/** 
 * 角色资源Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class OrgTreeSaveBo {
	
	private static final long serialVersionUID = 1L;
	@Field(description="机构树类型ID")
	private String orgTreeTypeId;

	@Field(description="机构树集合")
	List<OrgTreeBo> orgTrees;


}
