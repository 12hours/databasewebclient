package client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/datasource.properties")
public class DataSourceConfig {

    @Autowired
    private Environment env;


    @Bean
    DataSource dataSource() {
        final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(
                env.getProperty("db.url"), env.getProperty("db.username"), env.getProperty("db.password")
        );
        driverManagerDataSource.setDriverClassName(env.getProperty("db.driver"));
        return driverManagerDataSource;
    }

    @Bean
    JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }
}
