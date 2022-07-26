package dev.cinnes.marvel.controller;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static dev.cinnes.marvel.CharacterUtils.generateCharacter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CharacterController.class)
public class CharacterControllerTests {

    @MockBean
    private CharacterRepository characterRepository;

    @Autowired
    private WebTestClient client;

    private final Map<Integer, MarvelCharacter> data = Map.of(
            123, generateCharacter(123),
            124, generateCharacter(124),
            125, generateCharacter(125));

    @Test
    public void shouldListAllIds() {
        // given
        when(characterRepository.findAll()).thenReturn(Flux.fromIterable(data.values()));

        // when
        final var res = client
                .get()
                .uri("/characters")
                .exchange();

        // then
        res
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(3)
                .consumeWith(r -> r.getResponseBody().forEach(i -> assertThat(data).containsKey(i)));

        verify(characterRepository).findAll();
    }

    @Test
    public void shouldListAllIdsEmpty() {
        // given
        when(characterRepository.findAll()).thenReturn(Flux.empty());

        // when
        final var res = client
                .get()
                .uri("/characters")
                .exchange();

        // then
        res
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Integer.class)
                .hasSize(0);

        verify(characterRepository).findAll();
    }

    @Test
    public void shouldShow() {
        // given
        when(characterRepository.findById(124, "en")).thenReturn(Mono.just(data.get(124)));

        // when
        final var res = client
                .get()
                .uri("/characters/" + 124)
                .exchange();

        // then
        res
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MarvelCharacter.class)
                .consumeWith(r -> assertThat(r.getResponseBody()).isEqualTo(data.get(124)));

        verify(characterRepository).findById(124, "en");
    }

    @Test
    public void shouldShowEmpty() {
        // given
        when(characterRepository.findById(124, "en")).thenReturn(Mono.empty());

        // when
        final var res = client
                .get()
                .uri("/characters/" + 124)
                .exchange();

        // then
        res
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

        verify(characterRepository).findById(124, "en");
    }

    @Test
    public void shouldShowTranslation() {
        // given
        when(characterRepository.findById(124, "it")).thenReturn(Mono.just(data.get(124)));

        // when
        final var res = client
                .get()
                .uri("/characters/" + 124 + "?language=it")
                .exchange();

        // then
        res
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MarvelCharacter.class)
                .consumeWith(r -> assertThat(r.getResponseBody()).isEqualTo(data.get(124)));

        verify(characterRepository).findById(124, "it");
    }
}
