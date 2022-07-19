package dev.cinnes.marvel.config;

import dev.cinnes.marvel.utils.Md5Utils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class MarvelClientConfiguration {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public WebClient marvelClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(appProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .uriBuilderFactory(new DefaultUriBuilderFactory(uriComponentsBuilder()))
                .build();
    }

    private UriComponentsBuilder uriComponentsBuilder() {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        return UriComponentsBuilder
                .fromHttpUrl(appProperties.getBaseUrl())
                .queryParam("limit", appProperties.getPageLimit())
                .queryParam("ts", timestamp)
                .queryParam("apikey", appProperties.getPublicKey())
                .queryParam("hash", Md5Utils.hash(timestamp + appProperties.getPrivateKey() + appProperties.getPublicKey()));
    }
}
