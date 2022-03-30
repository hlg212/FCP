package io.github.hlg212.basic.model.po;

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
 * @date 2022-03-28
 */
@Table("T_APP")
@Data 
public class App extends Model {

	private static final long serialVersionUID = 1L;

	@PkId
	@Field(description="id")
	private String id;

	@Field(description="应用名称")
	private String name;

	@Field(description="应用代码")
	private String code;

	@Field(description="应用类型")
	private String type;

	@Field(description="首页地址")
	private String indexUrl;

	@Field(description="认证地址")
	private String loginUrl;

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
