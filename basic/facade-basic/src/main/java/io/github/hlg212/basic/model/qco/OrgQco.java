package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * 机构Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class OrgQco extends Qco {
	
	private static final long serialVersionUID = 1L;
	@Field(description = "名称模糊匹配")
	private String nameLike;
	@Field(description = "代码模糊匹配")
	private String codeLike;
	@Field(description = "类型等于")
	private String type;

	@Field(description = "创建时间排序")
	private String createTimeOrder="desc";
}
