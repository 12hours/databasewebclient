package client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Map;

//@EnableWebMvc // - disable this annotation to use standard spring-boot configuration for web apps
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter{

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPathMatcher(getPathMatcher());
    }

    private PathMatcher getPathMatcher() {
        return new AntPathMatcher() {
            @Override
            protected boolean doMatch(String pattern, String path, boolean fullMatch, Map<String, String> uriTemplateVariables) {
                return super.doMatch(pattern.toLowerCase(), path.toLowerCase(), fullMatch, uriTemplateVariables);
            }
        };
    }
}
