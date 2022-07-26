package dev.cinnes.marvel.repository;

import dev.cinnes.marvel.Constants;
import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.service.GoogleTranslateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class CharacterRepository {

    // data stored as:
    // languageCode#id
    // e.g, en#1234

    private final ReactiveRedisOperations<String, MarvelCharacter> characterOps;

    private final GoogleTranslateService googleTranslateService;

    public Mono<MarvelCharacter> save(final MarvelCharacter character, final String language) {
        log.debug("Saving {} with language {}.", character, language);
        return characterOps
                .opsForValue()
                .set(key(character, language), character)
                .thenReturn(character);
    }

    public Mono<MarvelCharacter> findById(final int id, final String language) {
        return characterOps
                .opsForValue()
                .get(key(id, language))
                // defer switch to avoid eager evaluation of alternative
                .switchIfEmpty(Mono.defer(() -> this.translateAndSave(id, language)));
    }

    public Flux<MarvelCharacter> findAll() {
        return characterOps.keys("en*").flatMap(characterOps.opsForValue()::get);
    }

    private Mono<MarvelCharacter> translateAndSave(final int id, final String language) {
        return characterOps
                .opsForValue()
                // get EN version
                .get(key(id, Constants.DEFAULT_LANGUAGE))
                // fetch translation
                .flatMap(c -> googleTranslateService.translate(c, language))
                // cache translation
                .flatMap(c -> this.save(c, language));
    }

    private String key(final MarvelCharacter character, final String language) {
        return key(character.id(), language);
    }

    private String key(final int id, final String language) {
        return language + "#" + id;
    }
}
