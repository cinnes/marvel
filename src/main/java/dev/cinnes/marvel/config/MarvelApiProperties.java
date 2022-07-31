package dev.cinnes.marvel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "marvel-api")
public record MarvelApiProperties(String baseUrl, int pageLimit, String publicKey, String privateKey) {}
