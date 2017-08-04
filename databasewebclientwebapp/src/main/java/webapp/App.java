package webapp;

import core.config.DataSourceConfig;
import core.config.JpaConfig;
import core.config.ModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@Import({DataSourceConfig.class, JpaConfig.class, ModuleConfig.class})
//@ComponentScans(value = {
//        @ComponentScan(resourcePattern = "**/config/*.class"),
//        @ComponentScan(basePackageClasses = App.class)
//        }
//)
@ComponentScan(basePackages = {"core.config", "webapp"})
public class App {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setWebEnvironment(true);
        app.run(args);
    }
}
