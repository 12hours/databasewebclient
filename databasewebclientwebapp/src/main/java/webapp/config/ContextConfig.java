package webapp.config;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webapp.data.OnlyIdFilter;

@Configuration
public class ContextConfig {

    @Bean
    FilterProvider filters(){
        return new SimpleFilterProvider().addFilter("OnlyIdFilter", new OnlyIdFilter());
    }

    @Bean
    MethodInvokingBean methodInvokingBean(){
        MethodInvokingBean methodInvokingBean = new MethodInvokingBean();
        methodInvokingBean.setStaticMethod("webapp.data.DTUtils.setFilters");
        methodInvokingBean.setArguments(new Object[] {filters()});
        return methodInvokingBean;
    }
}
