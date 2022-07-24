package io.github.hlg212.dam.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.annotation.Table;
import io.github.hlg212.fcf.model.BaseModel;
import io.github.hlg212.fcf.model.Model;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 数据权限配置
 * @author huangligui
 * @date 2021年06月01日
 */
@Data
@Table("T_DAM_CONFIG")
public class DamConfig extends BaseModel {

	@Field(description="应用编码")
	private String appId;

	@Field(description="名称")
	private String name;

	@Field(description="编码")
	private String code;

	@Field(description="查询是否控制")
	private String isApplyQuery;

	@Field(description="修改是否控制")
	private String isApplyUpdate;

	@Field(description="新增是否控制")
	private String isApplyAdd;

	@Field(description="删除是否控制")
	private String isApplyDelete;

}
