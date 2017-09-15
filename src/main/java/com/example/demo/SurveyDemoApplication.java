package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SurveyDemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SurveyDemoApplication.class, args);
    }
}
