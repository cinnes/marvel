package dev.cinnes.marvel;

import dev.cinnes.marvel.config.MarvelApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(MarvelApiProperties.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public OpenAPI openApi(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI().info(new Info()
                .title("Marvel Characters API")
                .description("API to facilitate fetching Marvel Characters and translating their descriptions.")
                .version(appVersion));
    }
}
