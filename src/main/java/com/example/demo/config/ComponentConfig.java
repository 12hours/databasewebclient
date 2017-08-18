package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ComponentConfig {

    @Bean(name = "entityHashMap")
    public Map entityManagerMap(){
        return Collections.synchronizedMap(new HashMap<String, EntityManager>());
    }

}
