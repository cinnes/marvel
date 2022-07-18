package dev.cinnes.marvel.controller;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.client.CharacterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterClient characterService;

    @GetMapping
    public Flux<MarvelCharacter> list() {
        return characterService.list();
    }
}
