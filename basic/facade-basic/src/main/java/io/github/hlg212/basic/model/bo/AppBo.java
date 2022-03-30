package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.App;
import io.github.hlg212.fcf.model.basic.IApp;

/** 
 * 应用Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
public class AppBo extends App implements IApp {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getLoginPage() {
		return getLoginUrl();
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getIndexPage() {
		return getIndexUrl();
	}
}
