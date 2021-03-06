package by.bockroir.app.config;

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
@EnableJpaRepositories(basePackages = "by.bockroir.app.dao")
public class JpaConfig {

    private static final Logger log = LoggerFactory.getLogger(JpaConfig.class);

    @Autowired
    DataSource dataSource;

    @Bean(name = "entityManagerFactory")
    @Profile({"prod", "staging"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        log.debug("SETTING UP HIBERNATE...");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("by.bockroir.app.domain");
        emf.setJpaProperties(new Properties(){
            {
                put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                put("hibernate.hbm2ddl.auto", "update");
                put("hibernate.enable_lazy_load_no_trans", "true");
            }
        });
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        return emf;
    }

    @Bean(name = "entityManagerFactory")
    @Profile("dev")
    public LocalContainerEntityManagerFactoryBean devEntityManagerFactory(){
        log.debug("SETTING UP HIBERNATE...");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("by.bockroir.app.domain");
        emf.setJpaProperties(new Properties(){
            {
                put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                put("hibernate.hbm2ddl.auto", "create");
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
        emf.setPackagesToScan("by.bockroir.app.domain");
        emf.setJpaProperties(new Properties(){
            {
                put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                put("hibernate.hbm2ddl.auto", "create");
                put("use_sql_comments", "true");
            }
        });
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        return emf;
    }


    @Bean
    @Profile({"test","dev","prod"})
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emfb) {
//        return new org.springframework.orm.hibernate5.HibernateTransactionManager(emfb.getObject());
        return new JpaTransactionManager(emfb.getObject());
    }

}