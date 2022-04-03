package io.github.hlg212.gatewayAdmin.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;
import java.util.Date;

/** 
 * 应用 
 * 
 * @author huanglg
 * @date 2022-04-03
 */
@Table("T_GA_ANONYMOUS_RES")
@Data 
public class GaAnonymousRes extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="名称")
	private String name;

	@Field(description="路径表达式")
	private String matchExpression;

	@Field(description="是否启用")
	private String isEnabled;

	@Field(description="备注")
	private String memo;

	@Field(description="排序")
	private Integer sortNum;

	@Field(description="创建时间")
	private Date createTime;

	@Field(description="创建人名称")
	private String createUserName;

	@Field(description="修改时间")
	private Date updateTime;

	@Field(description="修改人名称")
	private String updateUserName;


}
