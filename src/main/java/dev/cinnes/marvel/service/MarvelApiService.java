package dev.cinnes.marvel.service;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.model.response.ListCharactersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarvelApiService {

    private final WebClient marvelClient;

    private final String PATH = "/characters";

    /**
     * Fetches all Marvel characters from the Marvel API. This (as of writing) sends ~11 requests, so call sparingly.
     *
     * Uses the `total` value returned on the first page of results to determine how many pages to fetch, then spins
     * the fetches off into a Flux.
     *
     * @return Stream of Marvel characters (`MarvelCharacter`).
     */
    public Flux<MarvelCharacter> findAll() {
        // fetch first page to get `total`, then process remaining pages in parallel
        return fetch(PATH).flatMapMany(res -> {
            final var data = res.data();

            Flux<ListCharactersResponse> responses = Flux
                    .range(1, data.total() / data.count()) // page numbers
                    .flatMap(i -> {
                        final var offset = i * data.count();
                        return fetch(String.format("%s?offset=%d", PATH, offset));
                    });

            return Flux
                    .concat(Mono.just(res), responses)
                    .flatMapIterable(r -> r.data().results());
        });
    }

    private Mono<ListCharactersResponse> fetch(String url) {
        return marvelClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ListCharactersResponse.class);
    }
}
