package io.github.hlg212.basic.model.po;

import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.annotation.PkId;
import io.github.hlg212.fcf.model.Model;
import io.github.hlg212.fcf.annotation.Table;
import lombok.Data;
import java.util.Date;

/** 
 * 用户 
 * 
 * @author huanglg
 * @date 2022-03-28
 */
@Table("T_USER")
@Data 
public class User extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="名称")
	private String name;

	@Field(description="邮箱")
	private String email;

	@Field(description="电话")
	private String phone;

	@Field(description="密码")
	private String passwd;

	@Field(description="账号")
	private String account;

	@Field(description="机构ID")
	private String orgId;

	@Field(description="是否超级管理员")
	private String isAdmin;

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
