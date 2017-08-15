package com.example.demo.config;

import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ComponentConfig {

    @Bean
    public Map entityManagerMap(){
        return Collections.synchronizedMap(new HashMap<String, EntityManager>());
    }

}
