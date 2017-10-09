package by.bockroir.app.components;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class AppInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();

        // converting backup interval to minutes by overriding property
        propertySources.addFirst(new PropertySource<String>("custom") {
            private final String interval = (environment.getProperty("app.backup.interval") == null) ? "60" :
                    environment.getProperty("app.backup.interval");

            @Override
            public String getProperty(String name) {
                if (name.equals("app.backup.interval")) {
                    return Integer.toString(Integer.valueOf(interval) * 1000 * 60);
                }
                return null;
            }
        });
    }

}
