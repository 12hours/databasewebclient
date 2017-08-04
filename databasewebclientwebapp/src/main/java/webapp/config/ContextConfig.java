package webapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import webapp.data.OnlyIdFilter;

@Configuration
public class ContextConfig {

    @Bean
    FilterProvider filters(){
        return new SimpleFilterProvider().addFilter("OnlyIdFilter", new OnlyIdFilter());
    }


    @Bean
    public ObjectMapper objectMapper(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.filters(filters());
        return builder.build();
    }

    @Bean
    MethodInvokingBean methodInvokingBean(){
        MethodInvokingBean methodInvokingBean = new MethodInvokingBean();
        methodInvokingBean.setStaticMethod("webapp.data.DTUtils.setObjectMapper");
        methodInvokingBean.setArguments(new Object[] {objectMapper()});
        return methodInvokingBean;
    }
}
