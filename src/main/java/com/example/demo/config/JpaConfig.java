package com.example.demo.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.dao")
public class JpaConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);

    @Autowired
    DataSource dataSource;

    @Bean(name = "entityManagerFactory")
    @Profile("dev")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        log.debug("SETTING UP HIBERNATE...");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.demo.domain");
        emf.setJpaProperties(new Properties(){
            {
                put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                put("hibernate.hbm2ddl.auto", "update");
                put("hibernate.show_sql", "true");
            }
        });
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        return emf;
    }


    @Bean(name = "entityManagerFactory")
    @Profile("test")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryForH2(){
        log.debug("SETTING UP HIBERNATE...");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example.demo.domain");
        emf.setJpaProperties(new Properties(){
            {
                put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                put("hibernate.hbm2ddl.auto", "create");
                put("hibernate.show_sql", "true");
            }
        });
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        return emf;
    }

    @Bean(name = "entityManagerFactory")
    @Profile("prod")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryForHSQLDB(){
        log.debug("SETTING UP HIBERNATE...");
        //TODO
        return null;
    }

    @Bean
    @Profile({"test","dev","prod"})
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emfb) {
        return new JpaTransactionManager(emfb.getObject());
    }

}