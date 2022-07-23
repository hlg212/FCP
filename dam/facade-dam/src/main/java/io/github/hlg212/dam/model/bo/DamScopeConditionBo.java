
package io.github.hlg212.dam.model.bo;

import io.github.hlg212.dam.model.po.DamScopeCondition;
import io.github.hlg212.fcf.model.dam.DataAuthorityPropertyConditionValue;
import io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyCondition;
import io.github.hlg212.fcf.model.dam.IDataAuthorityPropertyConditionValue;
import io.github.hlg212.fcf.util.BooleanHelper;
import org.apache.commons.lang.StringUtils;

/** 
 * DataAuthorityScopeConditionBo
 * 数据权限范围条件
 * @author huangligui
 * @date 2021年06月01日
 */
public class DamScopeConditionBo extends DamScopeCondition implements IDataAuthorityPropertyCondition {

	private static final long serialVersionUID = 1L;

	@Override
	public String getCode() {
		return getId();
	}


	@Override
	public IDataAuthorityPropertyConditionValue getValue() {
		String value = getScopeValue();
		if(StringUtils.isNotEmpty(value))
		{
			DataAuthorityPropertyConditionValue val = new DataAuthorityPropertyConditionValue();
			val.setValue(value);
			return val;
		}
		return null;
	}

	@Override
	public String getType() {
		return getScopeType();
	}


	@Override
	public Boolean getIsQuery() {
		return BooleanHelper.to(getIsApplyQuery());
	}

	@Override
	public Boolean getIsDelete() {
		return BooleanHelper.to(getIsApplyDelete());
	}

	@Override
	public Boolean getIsUpdate() {
		return BooleanHelper.to(getIsApplyUpdate());
	}

	@Override
	public Boolean getIsAdd() {
		return BooleanHelper.to(getIsApplyAdd());
	}
}
