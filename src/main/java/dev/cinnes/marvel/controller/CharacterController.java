package dev.cinnes.marvel.controller;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    @GetMapping
    public Flux<Integer> listAllIds() {
        return characterRepository.findAll().map(MarvelCharacter::getId);
    }

    @GetMapping("/{characterId}")
    public Mono<ResponseEntity<MarvelCharacter>> show(@PathVariable Integer characterId) {
        return characterRepository
                .findById(characterId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}