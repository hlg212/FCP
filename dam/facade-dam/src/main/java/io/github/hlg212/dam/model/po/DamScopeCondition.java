package io.github.hlg212.dam.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.Table;
import io.github.hlg212.fcf.model.BaseModel;
import lombok.Data;

/** 
 *  数据权限范围条件
 * 
 * @author huangligui
 * @date 2021年06月01日
 */
@Data
@Table("T_DAM_SCOPE_CONDITION")
public class DamScopeCondition extends BaseModel {

	@Field(description="配置id")
	private String configId;
	@Field(description="名称")
	private String name;
	@Field(description="编码")
	private String code;

	@Field(description="条件类型(基础、合并、指定)")
	private String conditionType;

	@Field(description="属性名")
	private String propertyName;
	@Field(description="操作符(条件)")
	private String operation;
	@Field(description="范围参数编码")
	private String paramCode;

	@Field(description="值类型(动态,静态)")
	private String valueType;

	@Field(description="范围值")
	private String scopeValue;

	@Field(description="范围类型(机构、数字、时间)")
	private String scopeType;
	@Field(description="应用到查询")
	private String isApplyQuery;
	@Field(description="应用到新增")
	private String isApplyAdd;
	@Field(description="应用到修改")
	private String isApplyUpdate;
	@Field(description="应用到删除")
	private String isApplyDelete;


}
