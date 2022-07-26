package dev.cinnes.marvel.controller;

import dev.cinnes.marvel.Constants;
import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/characters", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterRepository characterRepository;

    @GetMapping
    public Flux<Integer> listAllIds() {
        return characterRepository.findAll().map(MarvelCharacter::id);
    }

    @GetMapping("/{characterId}")
    public Mono<ResponseEntity<MarvelCharacter>> show(
            @PathVariable int characterId,
            @RequestParam(defaultValue = Constants.DEFAULT_LANGUAGE) String language) {
        return characterRepository
                .findById(characterId, language)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}