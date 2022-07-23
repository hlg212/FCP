
package io.github.hlg212.dam.model.bo;

import io.github.hlg212.dam.model.po.DamConfig;
import io.github.hlg212.fcf.model.dam.IDataAuthorityConfigSet;
import io.github.hlg212.fcf.util.BooleanHelper;
import lombok.Data;

/** 
 * DamConfigBo
 * 数据权限模块配置
 *
 * @author huangligui
 * @date 2021年06月01日
 */
@Data
public class DamConfigBo extends DamConfig implements IDataAuthorityConfigSet {


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
