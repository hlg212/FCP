package io.github.hlg212.task.conf;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import io.github.hlg212.task.properties.JobProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@EnableConfigurationProperties(JobProperties.class)
public class XxlJobConfig {
    @Autowired
    private JobProperties jobProperties;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(jobProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(jobProperties.getAppName());
        xxlJobSpringExecutor.setIp(jobProperties.getIp());
        xxlJobSpringExecutor.setPort(jobProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(jobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(jobProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(jobProperties.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
