package com.yhao.webdemo.dao.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.yhao.webdemo.dao.dynamic.DataSourceEnum;
import com.yhao.webdemo.dao.dynamic.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.yhao.webdemo.dao.config.DataSource1Config.getBatisConfig;

@ConditionalOnProperty(prefix = "datasource", name = "mode", havingValue = "dynamic")
@Configuration
@MapperScan("com.yhao.webdemo.dao.mapper.db1")
public class DynamicDataSourceConfig {

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.database.master")
    @Primary
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.database.slave")
    @Primary
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean("sqlSessionFactoryDynamic")
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource);
        sqlSessionFactory.setConfiguration(getBatisConfig());
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean(value = "transactionManager")
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Primary
    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("master") DataSource masterDataSource,
                                               @Autowired(required = false) @Qualifier("slave") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceEnum.MASTER.getName(), masterDataSource);
        if (slaveDataSource != null) {
            targetDataSources.put(DataSourceEnum.SLAVE.getName(), slaveDataSource);
        }
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }
}
