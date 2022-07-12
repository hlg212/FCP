/**
 * 
 */
package io.github.hlg212.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "hlg212.cas.password-error")
@Component
@Data
@RefreshScope
public class LoginPasswordErrorProperties {

	// 时间范围内最大错误次数
	private Integer maxErrorNum = 5;

	// 锁定分钟
	private Integer lockMinutes = 45;

	private Integer timecell = 5;

	private Boolean enable = true;

}
