package dev.cinnes.marvel.repository;

import dev.cinnes.marvel.model.MarvelCharacter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// TODO: cinnes: cache these calls? h2 in mem is just as fast
//@CacheConfig(cacheNames = {"Character"})
public interface CharacterRepository extends ReactiveCrudRepository<MarvelCharacter, Integer> {
}