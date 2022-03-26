package io.github.hlg212.generator.conf;

import io.github.hlg212.fcf.core.RoutingDataSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;

@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DateSouceConfig {

    @Bean
    @Primary
    public DataSource routingDataSource(BeanFactory beanFactory,@Lazy  DataSource dataSource) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(new HashMap());
        routingDataSource.setDefaultTargetDataSource(dataSource);
        return routingDataSource;
    }




}
