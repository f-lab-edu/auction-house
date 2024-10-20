package com.infra.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "com.infra")
public class LazyDataSourceConfiguration {
    @Bean
    public HikariDataSource hikariDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy(HikariDataSource hikariDataSource) {
        return new LazyConnectionDataSourceProxy(hikariDataSource);
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("lazyConnectionDataSourceProxy") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    )  {
        return builder.dataSource(dataSource)
                .packages("com.domain")
                .persistenceUnit("main")
                .build();
    }
}

