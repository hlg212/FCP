package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.OrgTree;
import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.basic.ITree;
import io.github.hlg212.fcf.model.basic.Org;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/** 
 * 机构树Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class OrgTreeBo extends OrgTree implements ITree {
	
	private static final long serialVersionUID = 1L;

	@Field(description="名称")
	private String name;

	@Field(description="编码")
	private String code;

	@Field(description="简称")
	private String shortName;

	@Field(description="机构")
	private OrgBo org;

	private List<OrgTreeBo> children;

	public String getName(){
		if(!Objects.isNull(org)){
			return org.getName();
		}
		return null;
	}

	@Override
	public String getPid() {
		return getParentOrgId();
	}

	@Override
	public  List<OrgTreeBo> getChildren() {
		return children;
	}
}
