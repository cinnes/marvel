package dev.cinnes.marvel.initialize;

import dev.cinnes.marvel.service.CharacterService;
import dev.cinnes.marvel.repository.CharacterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@Slf4j
public class CharacterInitializer implements CommandLineRunner {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterService characterService;

    @Override
    public void run(String... args) {
        log.info("Pre-loading characters");
        characterRepository.deleteAll()
                .thenMany(characterService.findAll())
                .flatMap(characterRepository::save)
                .then(characterRepository.findAll().collectList())
                .subscribe(characters -> log.info("Pre-loaded {} Characters", characters.size()));
    }
}
