package com.yhao.webdemo.dao.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }

    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }
}
