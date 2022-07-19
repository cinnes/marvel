package dev.cinnes.marvel.repository;

import dev.cinnes.marvel.model.MarvelCharacter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class CharacterRepository {
    @Autowired
    private ReactiveRedisOperations<String, MarvelCharacter> characterOps;

    public Mono<Void> save(MarvelCharacter character) {
        return characterOps
                .opsForValue()
                .set(key(character), character)
                .and(this.saveToList(character));
    }

    public Mono<MarvelCharacter> findById(final Integer id) {
        return characterOps.opsForValue().get(key(id));
    }

    public Flux<MarvelCharacter> findAll() {
        return characterOps.opsForList().range("characters", 0, -1);
    }

    private Mono<Long> saveToList(MarvelCharacter character) {
        // NOTE: could technically just cache ids here, would be a bit more optimal. left as is for brevity.
        return characterOps.opsForList().rightPush("characters", character);
    }

    private String key(MarvelCharacter character) {
        return key(character.getId());
    }

    private String key(final Integer id) {
        return "character#" + id;
    }
}
