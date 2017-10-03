package by.bockroir.app;

import by.bockroir.app.components.AppInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.addInitializers(new AppInitializer());
        app.run(args);
        //        SpringApplication.run(Application.class, args);

    }
}
