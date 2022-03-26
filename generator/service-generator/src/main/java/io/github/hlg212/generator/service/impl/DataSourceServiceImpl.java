package io.github.hlg212.generator.service.impl;

import io.github.hlg212.generator.model.bo.DbBo;
import io.github.hlg212.generator.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private EnvironmentManager environment;

    @Autowired
    private RefreshScope refreshScope;

    public void configDb(DbBo db)
    {
        environment.setProperty("spring.datasource.url",db.getDbUrl());
        environment.setProperty("spring.datasource.username",db.getUsername());
        environment.setProperty("spring.datasource.password",db.getPassword());
        refreshScope.refreshAll();
    }


}
