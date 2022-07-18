package dev.cinnes.marvel.client;

import dev.cinnes.marvel.model.MarvelCharacter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Service
@Slf4j
public class CharacterClient {

    private WebClient client = WebClient.create();

    public Flux<MarvelCharacter> list() {
        return Flux.fromIterable(
                Arrays.asList(
                new MarvelCharacter("Spiderman"),
                new MarvelCharacter("Octopussy"),
                new MarvelCharacter("Niglord"))
        );
    }
}
