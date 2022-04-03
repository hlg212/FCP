package io.github.hlg212.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "fcf.gateway.security.blackWhiteList")
@Data
public class BlackWhiteListProperties {
	
	private Boolean globalEnabled ;
	private Boolean appEnabled ;
	private Boolean userEnabled ;

}
