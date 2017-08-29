package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.yml","file:application.properties"})
public class SurveyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyDemoApplication.class, args);
    }
}
