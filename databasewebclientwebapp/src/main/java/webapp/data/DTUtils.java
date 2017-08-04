package webapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import core.domain.Child;
import core.domain.Survey;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

public class DTUtils {

//    static FilterProvider filters;
//
//    static void setFilters(FilterProvider filters){
//        DTUtils.filters = filters;
//    }
//
    static ObjectMapper objectMapper;

    static void setObjectMapper(ObjectMapper objectMapper){
        DTUtils.objectMapper =  objectMapper;
    }

    public static String childToJson(Child child) {
//        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(child);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String surveyToJson(Survey survey) {
//        ObjectMapper objectMapper = new ObjectMapper();
        Child child = survey.getChild();
        System.out.println(Hibernate.isPropertyInitialized(child, "name"));
        String json = null;
        try {
            json = objectMapper.writeValueAsString(survey);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(Hibernate.isPropertyInitialized(child, "name"));
        return json;
    }

    public static String objectToJson(Object o) {
//        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}







