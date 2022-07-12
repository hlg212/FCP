package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.App;
import io.github.hlg212.fcf.annotation.Field;
import io.github.hlg212.fcf.model.basic.IApp;
import lombok.Data;

import java.util.List;

/** 
 * 应用Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class AppResBo {
	
	private static final long serialVersionUID = 1L;

	@Field(description="应用")
	private AppBo app;

	@Field(description="资源树")
	private List<ResBo> resTree;


}
