package client;

import client.domain.Child;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {Child.class, Jsr310JpaConverters.class})
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setWebEnvironment(true);
        app.run(args);
    }
}
