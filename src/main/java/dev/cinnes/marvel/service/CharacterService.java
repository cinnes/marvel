package dev.cinnes.marvel.service;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.model.response.ListCharactersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CharacterService {

    @Autowired
    private WebClient marvelClient;

    private final String PATH = "/characters";

    public Flux<MarvelCharacter> findAll() {
        return fetchItems(PATH)
                .expand(res -> {
                    if (res.getData().hasMore()) {
                        var offset = res.getData().nextOffset();
                        return fetchItems(String.format("%s?offset=%d", PATH, offset));
                    } else {
                        return Mono.empty();
                    }
                }).flatMap(res -> Flux.fromIterable(res.getData().getResults()));
    }

    private Mono<ListCharactersResponse> fetchItems(String url) {
        return marvelClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ListCharactersResponse.class);
    }
}
