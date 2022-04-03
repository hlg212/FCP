package io.github.hlg212.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "fcf.gateway.security")
@Data
public class  GatewaySecurityProperties {
	
	private Boolean enabled;
	// 接口权限验证开启，默认开启
	private Boolean urlAuthEnabled;
	// 没有权限时（403）也支持跨域。 默认开启
	private Boolean corsAccessDeniedEnabled;

	private List<String> anonymous;
	private BlackWhiteListProperties blackWhiteList;
}
