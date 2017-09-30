package by.bockroir.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    Environment env;

    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        log.debug("CREATING IN-MEMORY H2 DB...");
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb")
                .build();
    }

    @Bean
    @Profile("dev")
    public DataSource devDataSource() throws IOException, URISyntaxException {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb")
//                .addScript(org.apache.commons.io.FileUtils.readFileToString(
//                        new File(this.getClass().getResource("/func.sql").getPath()),
//                        StandardCharsets.UTF_8))
                .build();
    }

    @Bean
    @Profile("staging")
    public DataSource stagingDataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:file:" + env.getProperty("app.database.file"))
                .build();
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:file:" + env.getProperty("app.database.file"))
                .build();
    }



}
