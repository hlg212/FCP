package io.github.hlg212.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = OAuthTokenProperties.PREFIX)
@Data
public class OAuthTokenProperties {

	public static final String PREFIX = "fcp.cas.oauth.token";

	//token过期时间
	private int accessTokenValiditySeconds = -1;
	// 刷新token过期时间
	private int refreshTokenValiditySeconds = -1;

}
