package com.demo.db.multipledb.postgres.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "secondentityManagerFactoryBean",
        basePackages = {"com.demo.db.multipledb.postgres.repository"},
        transactionManagerRef = "secondtransactionManger")
public class PostgresDbConfig {
    //datasource bean

    @Autowired
    private Environment environment;

    @Bean(name = "secondDataSource")
    @Primary
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.d2.datasource.url"));
        dataSource.setDriverClassName(environment.getProperty("spring.d2.datasource.driver-class-name"));
        dataSource.setUsername(environment.getProperty("spring.d2.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.d2.datasource.password"));



        return dataSource;
    }


    //entity manager bean


    @Bean(name="secondentityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
    {
        LocalContainerEntityManagerFactoryBean bean=new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        bean.setPackagesToScan("com.demo.db.multipledb.postgres.entites");
        Map<String,String> props=new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","update");
        bean.setJpaPropertyMap(props);
        return  bean;
    }


    //platform Transaction manager

    @Bean(name="secondtransactionManger")
    @Primary
    public PlatformTransactionManager transactionManager()
    {
        JpaTransactionManager manager=new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;
    }
}
