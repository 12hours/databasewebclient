package com.example.demo;

import com.example.demo.domain.Child;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.GeneratedValue;
import java.lang.annotation.Annotation;

@SpringBootApplication
public class SurveyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyDemoApplication.class, args);
    }
}
