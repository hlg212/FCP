package io.github.hlg212.basic.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;
import java.util.Date;

/** 
 * 系统配置 
 * 
 * @author huanglg
 * @date 2022-03-28
 */
@Table("T_SYS_CONFIG")
@Data 
public class SysConfig extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="ID")
	private String id;

	@Field(description="名称")
	private String name;

	@Field(description="编码")
	private String code;

	@Field(description="值")
	private String val;

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
