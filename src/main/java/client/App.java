package client;

import client.config.DataSourceConfig;
import client.config.JpaConfig;
import client.config.WebAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@SpringBootApplication
@Import({DataSourceConfig.class, JpaConfig.class, WebAppConfig.class})
@PropertySource("classpath:/app.properties")
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setWebEnvironment(true);
        app.run(args);
    }
}
