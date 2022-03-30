package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.Res;
import io.github.hlg212.fcf.model.basic.IRes;
import lombok.Data;

import java.util.List;

/** 
 * 资源Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class ResBo extends Res implements IRes {
	
	private static final long serialVersionUID = 1L;

	private List<ResBo> childrens;

	@Override
	public String getCode() {
		return getPermissionCode();
	}

	@Override
	public String getPath() {
		return getUrl();
	}

	@Override
	public String getPid() {
		return getParentResId();
	}

	@Override
	public List<ResBo> getChildren() {
		return this.childrens;
	}
}
