package io.github.hlg212.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = JobProperties.PREFIX)
@Data
public class JobProperties {

	public static final String PREFIX = "fcp.task.job";

	private String adminAddresses;

	private String appName;

	private String ip;

	private int port;

	private String accessToken;

	private String logPath;

	private int logRetentionDays;
}
