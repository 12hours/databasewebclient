package com.example.demo.config;

import com.example.demo.domain.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Child.class);
        config.exposeIdsFor(Survey.class);
        config.exposeIdsFor(Diagnosis.class);
        config.exposeIdsFor(Disorder.class);
        config.exposeIdsFor(EducationProgram.class);
        config.exposeIdsFor(Recommendation.class);
    }
}