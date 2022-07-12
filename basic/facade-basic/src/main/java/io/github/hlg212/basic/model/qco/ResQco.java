package io.github.hlg212.basic.model.qco;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

/** 
 * 资源Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class ResQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	@Field(description = "应用")
	private String appId;

	@Field(description = "上级ID")
	private String parentResId;

	@Field(description = "名称模糊匹配")
	private String nameLike;

	@Field(description = "资源类型")
	private String type;

	@Field(description = "资源路径模糊匹配")
	private String urlLike;

	@Field(description = "权限代码匹配")
	private String permissionCodeLike;

	@Field(description = "权限代码是否为空")
	@JsonIgnore
	private String permissionCodeIs;

	@Field(description = "权限代码是否为空")
	@JsonIgnore
	private String urlIs;

	@Field(description = "创建时间排序")
	private String createTimeOrder="desc";



}
