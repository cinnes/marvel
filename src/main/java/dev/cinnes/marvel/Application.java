package dev.cinnes.marvel;

import dev.cinnes.marvel.config.AppProperties;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public GroupedOpenApi charactersOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/characters/**" };
		return GroupedOpenApi.builder().group("Characters")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Characters API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}
}
