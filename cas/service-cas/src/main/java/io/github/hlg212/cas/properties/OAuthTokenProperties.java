/**
 * 
 */
package io.github.hlg212.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "hlg212.oauth.token")
@Data
public class OAuthTokenProperties {

	//token过期时间
	private int accessTokenValiditySeconds = -1;
	// 刷新token过期时间
	private int refreshTokenValiditySeconds = -1;

}
