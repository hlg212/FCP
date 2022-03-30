package io.github.hlg212.basic.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;
import java.util.Date;

/** 
 * 资源 
 * 
 * @author huanglg
 * @date 2022-03-28
 */
@Table("T_RES")
@Data 
public class Res extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="所属应用")
	private String appId;

	@Field(description="上级资源")
	private String parentResId;

	@Field(description="资源名称")
	private String name;

	@Field(description="权限代码")
	private String permissionCode;

	@Field(description="资源图标")
	private String icon;

	@Field(description="资源URL")
	private String url;

	@Field(description="资源类型")
	private String type;

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
