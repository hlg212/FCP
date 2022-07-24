package io.github.hlg212.dam.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * DataAuthorityConfigQco
 *
 * @author huangligui
 * @date 2021年06月01日
 */
@Data
public class DamConfigQco extends Qco {

	private static final long serialVersionUID = 1L;

	@Field(description="应用编码")
	private String appCode;

	@Field(description="应用编码")
	private String appId;

	@Field(description="使用标志")
	private String isEnabled;

	@Field(description="编码Like")
	private String codeLike;
}
