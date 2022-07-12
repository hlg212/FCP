package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * 字典Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class DictQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	@Field(description = "应用")
	private String appId;

	@Field(description = "上级ID")
	private String parentDictId;

	@Field(description = "名称模糊匹配")
	private String nameLike;

	@Field(description = "编码模糊匹配")
	private String codeLike;

	@Field(description = "排序号排序")
	private String sortNumOrder="asc";

	@Field(description = "创建时间排序")
	private String createTimeOrder="desc";
}
