package io.github.hlg212.gatewayAdmin.model.bo;

import io.github.hlg212.fcf.model.ga.IRoute;
import io.github.hlg212.gatewayAdmin.model.po.GaRoute;

/** 
 * 应用Bo
 *
 * @author huanglg
 * @date 2022-04-03
 */
public class GaRouteBo extends GaRoute  implements IRoute {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMatchPath() {
		return getMatchExpression();
	}

	@Override
	public String getNmatchPath() {
		return getNotMatchExpression();
	}

	@Override
	public String getUri() {
		return getDestHost();
	}

	@Override
	public Long getOrder() {
		return (long)getSortNum();
	}
}
