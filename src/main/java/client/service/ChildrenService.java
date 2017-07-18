package client.service;

import client.dao.ChildDao;
import client.domain.Child;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

public abstract class ChildrenService {

    public Child createChildFromJson(String jsonString) {
        Child child = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            child = objectMapper.readValue(jsonString, Child.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return child;
    }
    
    public String createJsonFromChild(Child child){
        ObjectMapper objectMapper = new ObjectMapper();
        String childJsonString = null;
        try {
            childJsonString = objectMapper.writeValueAsString(child);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return childJsonString;
    }

    abstract public Child getChildById(long id);

    abstract public void saveChild(Child child);
}
