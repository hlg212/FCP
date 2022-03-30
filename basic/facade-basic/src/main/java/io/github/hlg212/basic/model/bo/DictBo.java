package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.Dict;
import io.github.hlg212.fcf.model.basic.IDict;
import lombok.Data;

import java.util.List;

/** 
 * 字典Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class DictBo extends Dict implements IDict {
	
	private static final long serialVersionUID = 1L;

	private List<DictBo> childrens;

	@Override
	public String getKey() {
		return getCode();
	}

	@Override
	public String getValue() {
		return getVal();
	}

	@Override
	public String getPid() {
		return getParentDictId();
	}

	@Override
	public List<DictBo> getChildren() {
		return childrens;
	}
}
