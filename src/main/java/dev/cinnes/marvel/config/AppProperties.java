package dev.cinnes.marvel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    @Getter
    @Setter
    private String baseUrl;
    @Getter
    @Setter
    private String publicKey;
    @Getter
    @Setter
    private String privateKey;
    @Getter
    @Setter
    private int pageLimit;
}
