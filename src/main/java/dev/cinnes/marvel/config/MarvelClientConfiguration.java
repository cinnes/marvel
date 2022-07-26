package dev.cinnes.marvel.config;

import dev.cinnes.marvel.utils.Md5Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@RequiredArgsConstructor
public class MarvelClientConfiguration {

    private final AppProperties appProperties;

    @Bean
    public WebClient marvelClient(WebClient.Builder builder) {
        return builder
                .baseUrl(appProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .uriBuilderFactory(new DefaultUriBuilderFactory(uriComponentsBuilder()))
                .build();
    }

    private UriComponentsBuilder uriComponentsBuilder() {
        final var timestamp = String.valueOf(System.currentTimeMillis());
        return UriComponentsBuilder
                .fromHttpUrl(appProperties.getBaseUrl())
                .queryParam("limit", appProperties.getPageLimit())
                .queryParam("ts", timestamp)
                .queryParam("apikey", appProperties.getPublicKey())
                .queryParam("hash", Md5Utils.hash(timestamp + appProperties.getPrivateKey() + appProperties.getPublicKey()));
    }
}
