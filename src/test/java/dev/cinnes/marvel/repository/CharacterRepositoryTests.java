package dev.cinnes.marvel.repository;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.service.GoogleTranslateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static dev.cinnes.marvel.CharacterUtils.generateCharacter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CharacterRepositoryTests {

    @SuppressWarnings("unchecked")
    private final ReactiveRedisOperations<String, MarvelCharacter> characterOps =
            mock(ReactiveRedisOperations.class);

    @SuppressWarnings("unchecked")
    private final ReactiveValueOperations<String, MarvelCharacter> valueOps =
            mock(ReactiveValueOperations.class);

    private final GoogleTranslateService googleTranslateService = mock(GoogleTranslateService.class);

    private final CharacterRepository characterRepository = new CharacterRepository(characterOps, googleTranslateService);



    @BeforeEach
    public void setup() {
        when(characterOps.opsForValue()).thenReturn(valueOps);
    }

    @Test
    public void shouldSave() {
        // given
        final var character = generateCharacter(123);

        when(valueOps.set("en#123", character)).thenReturn(Mono.just(true));

        // when
        final var result = characterRepository.save(character, "en");

        // then
        StepVerifier
                .create(result)
                .consumeNextWith(assertThat(character)::isEqualTo)
                .verifyComplete();

        verify(valueOps).set("en#123", character);
        verify(characterOps).opsForValue();
    }

    @Test
    public void shouldFindById() {
        // given
        final var character = generateCharacter(123);

        when(valueOps.get("en#123")).thenReturn(Mono.just(character));

        // when
        final var result = characterRepository.findById(character.id(), "en");

        // then
        StepVerifier
                .create(result)
                .consumeNextWith(assertThat(character)::isEqualTo)
                .verifyComplete();

        verify(valueOps).get("en#123");
        verify(characterOps).opsForValue();
        verifyNoMoreInteractions(characterOps, valueOps);
    }

    @Test
    public void shouldFindByIdMissing() {
        // given
        final var character = generateCharacter(123);

        when(valueOps.get("it#123")).thenReturn(Mono.empty());
        when(valueOps.get("en#123")).thenReturn(Mono.empty());

        // when
        final var result = characterRepository.findById(character.id(), "it");

        // then
        StepVerifier.create(result).verifyComplete();

        verify(valueOps).get("it#123");
        verify(valueOps).get("en#123");
        verify(characterOps, times(2)).opsForValue();
        verifyNoMoreInteractions(characterOps, valueOps);
        verifyNoInteractions(googleTranslateService);
    }

    @Test
    public void shouldFindByIdWithTranslation() {
        // given
        final var character = generateCharacter(123);
        final var translation =
                MarvelCharacter
                        .builder()
                        .id(character.id())
                        .name(character.name())
                        .description("pineapple on pizza: bueno")
                        .thumbnail(character.thumbnail())
                        .build();

        when(valueOps.get("it#123")).thenReturn(Mono.empty());
        when(valueOps.get("en#123")).thenReturn(Mono.just(character));
        when(valueOps.set("it#123", translation)).thenReturn(Mono.just(true));
        when(googleTranslateService.translate(character, "it")).thenReturn(Mono.just(translation));

        // when
        final var result = characterRepository.findById(character.id(), "it");

        // then
        StepVerifier.create(result).expectNext(translation).verifyComplete();

        verify(valueOps).get("it#123");
        verify(valueOps).get("en#123");
        verify(googleTranslateService).translate(character, "it");
        verify(valueOps).set("it#123", translation);
        verify(characterOps, times(3)).opsForValue();
        verifyNoMoreInteractions(valueOps, characterOps, googleTranslateService);
    }

    @Test
    public void shouldFindAll() {
        // given
        final var char1 = generateCharacter(123);
        final var char2 = generateCharacter(124);
        final var char3 = generateCharacter(125);
        final var chars =
                Map.of(
                        "en#123", char1,
                        "en#124", char2,
                        "en#125", char3
                );

        when(characterOps.keys("en*")).thenReturn(Flux.fromIterable(chars.keySet()));
        chars.forEach((key, value) -> when(valueOps.get(key)).thenReturn(Mono.just(value)));

        // when
        final var result = characterRepository.findAll();

        // then
        StepVerifier
                .create(result)
                .expectNextSequence(chars.values())
                .verifyComplete();

        verify(characterOps).keys("en*");
        verify(characterOps).opsForValue();
        chars.keySet().forEach(verify(valueOps)::get);
    }
}
