package by.bockroir.app.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

public class EnviromentConfigurer implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Iterator<PropertySource<?>> iterator = environment.getPropertySources().iterator();
        while (iterator.hasNext()) {
            PropertySource<?> next = iterator.next();
            Object source = next.getSource();
            System.out.println(source.toString());
        }
    }
}
