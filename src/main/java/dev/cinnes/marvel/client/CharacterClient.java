package dev.cinnes.marvel.client;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.model.response.ListCharactersResponse;
import dev.cinnes.marvel.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class CharacterClient {

    String baseUrl = "https://gateway.marvel.com/v1/public";
    String publicKey = "7b0e55f6a4544e6f2165a9359dabeb27";
    String privateKey = "8ec4e3d8cb93baaf0d005526c14c07ae8717cc78";
    String ts = "1";

    private WebClient client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .uriBuilderFactory(new DefaultUriBuilderFactory(uriComponentsBuilder()))
                .build();

    public Flux<MarvelCharacter> list() {
        return client.get()
            .uri("/characters")
            .exchangeToMono(res -> res.bodyToMono(ListCharactersResponse.class))
            .flatMapIterable(list -> list.getData().getResults());
    }

    private UriComponentsBuilder uriComponentsBuilder() {
        return UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("ts", ts)
                .queryParam("apikey", publicKey)
                .queryParam("hash", Md5Utils.hash(ts + privateKey + publicKey));
    }
}
