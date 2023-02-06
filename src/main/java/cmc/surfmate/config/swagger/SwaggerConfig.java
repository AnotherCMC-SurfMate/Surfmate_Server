package cmc.surfmate.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SwaggerConfig.java
 *
 * @author jemlog
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion)
    {

        Info info = new Info().title("Surfmate API Document").version(springdocVersion)
                .description("Surfmate API 문서입니다.")
                .contact(new Contact().name("Mocha").email("jemin03120111@gmail.com"));

        Server server1 = new Server();
        server1.setUrl("https://surfmate.life");

        Server server2 = new Server();
        server2.setUrl("http://localhost:8080");
        return new OpenAPI()
                .components(new Components())
                .servers(List.of(server1,server2))
                .info(info);


    }
}
