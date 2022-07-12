package io.github.hlg212.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@ConfigurationProperties(prefix = "hlg212.cas")
@Component
@Data
public class CasProperties {

	private String loginPage;
	//是否进行并发登录控制
	// 默认控制，不允许多session登录
	private Boolean concurrencyLoginControl = true;

	private Boolean filterSecurity = false;

	private List<String> webRequestMatchers;

	private List<String> anonymous;

	public void setLoginPageUrl(String loginPageUrl)
	{
		this.loginPage = loginPageUrl;
	}

}
