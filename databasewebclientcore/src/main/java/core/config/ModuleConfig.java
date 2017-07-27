package core.config;

import core.dao.ChildRepository;
import core.service.ChildService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "core.service")
@EnableJpaRepositories(basePackages = {"core.dao"})
public class ModuleConfig {
}
