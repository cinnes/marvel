package dev.cinnes.marvel.controller;

import dev.cinnes.marvel.annotations.ValidLanguage;
import dev.cinnes.marvel.utils.Constants;
import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.repository.CharacterRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping(value = "/characters", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterRepository characterRepository;

    @ApiResponse(responseCode = "200", description = "Fetch all MarvelCharacter ids.")
    @GetMapping
    public Flux<Integer> listAllIds() {
        return characterRepository.findAll().map(MarvelCharacter::id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show a specific MarvelCharacter"),
            @ApiResponse(responseCode = "404", description = "MarvelCharacter not found.")
    })
    @GetMapping("/{characterId}")
    public Mono<ResponseEntity<MarvelCharacter>> show(
            @PathVariable int characterId,
            @ValidLanguage @RequestParam(defaultValue = Constants.DEFAULT_LANGUAGE) String language) {

        return characterRepository
                .findById(characterId, language.toLowerCase())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}