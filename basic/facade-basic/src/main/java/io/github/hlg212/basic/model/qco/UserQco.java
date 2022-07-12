package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * 用户Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class UserQco extends Qco {
	
	private static final long serialVersionUID = 1L;
	@Field(description = "账号等于")
	private String account;

	@Field(description = "名称模糊匹配")
	private String nameLike;

	@Field(description = "账号模糊匹配")
	private String accountLike;

	@Field(description = "创建时间排序")
	private String createTimeOrder="desc";
}
