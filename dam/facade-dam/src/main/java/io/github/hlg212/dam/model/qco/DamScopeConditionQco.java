package io.github.hlg212.dam.model.qco;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

import javax.validation.constraints.NotNull;

/** 
 * DataAuthorityScopeConditionQco
 *
 * @author huangligui
 * @date 2021年06月01日
 */
@Data
public class DamScopeConditionQco extends Qco {

	private static final long serialVersionUID = 1L;

	@Field(description="配置id")
	private String configId;

	//@Field(description="使用标志")
	//private String sybz;

	@Field(description="属性名")
	private String propertyName;
	@Field(description="操作符(条件)")
	private String operation;

}
