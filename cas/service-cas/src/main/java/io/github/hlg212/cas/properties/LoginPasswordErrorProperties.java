/**
 * 
 */
package io.github.hlg212.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = LoginPasswordErrorProperties.PREFIX)
@Component
@Data
@RefreshScope
public class LoginPasswordErrorProperties {

	public static final String PREFIX = "fcp.cas.login";

	// 时间范围内最大错误次数
	private Integer maxErrorNum = 5;

	// 锁定分钟
	private Integer lockMinutes = 45;

	// 时间范围
	private Integer timecell = 5;

	private Boolean enable = true;

}
