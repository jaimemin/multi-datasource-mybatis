package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.config;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.repository.DataSourceRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TenantDatabaseConfig {

    @Bean
    public DynamicRoutingDataSource dynamicRoutingDataSource(DataSourceRepository repository) {
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        routingDataSource.init(repository);

        return routingDataSource;
    }

    @Bean
    public LazyConnectionDataSourceProxy lazyDataSource(DynamicRoutingDataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(LazyConnectionDataSourceProxy lazyDataSource) {
        return new DataSourceTransactionManager(lazyDataSource);
    }

    @Bean(name = "tenantSqlSessionFactory")
    public SqlSessionFactory tenantSqlSessionFactoryBean(LazyConnectionDataSourceProxy lazyDataSource
            , ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(lazyDataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/tenant/*.xml"));

        return factoryBean.getObject();
    }

    @Bean(name = "tenantSqlSession")
    public SqlSession tenantSqlSession(@Qualifier("tenantSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
