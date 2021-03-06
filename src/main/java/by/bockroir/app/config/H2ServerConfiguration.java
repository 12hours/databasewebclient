package by.bockroir.app.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * If you left the default port for the H2 web console at 8082 go to http://localhost:8082.
 * If you didn't change the name for the database in your properties file,
 * the default connection URL is jdbc:h2:mem:testdb and the username is 'sa', password empty.
 */
@Configuration
@Profile({"test","dev", "prod"}) // Only activate this in the "dev" profile
public class H2ServerConfiguration {

    // TCP port for remote connections, default 9092
    @Value("${h2.tcp.port}")
    private String h2TcpPort;

    // Web port, default 8082
    @Value("${h2.web.port}")
    private String h2WebPort;

    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * Connect to "jdbc:h2:tcp://localhost:9092/mem:testdb", username "sa", password empty.
     */
    @Bean
    @ConditionalOnExpression("${h2.tcp.enabled}")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort).start();
    }

    /**
     * Web console for the embedded h2 database.
     *
     * Go to http://localhost:8082 and connect to the database "jdbc:h2:mem:testdb", username "sa", password empty.
     */
    @Bean
    @ConditionalOnExpression("${h2.web.enabled}")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", h2WebPort).start();
    }
}