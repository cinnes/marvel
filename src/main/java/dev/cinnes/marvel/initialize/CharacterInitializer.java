package dev.cinnes.marvel.initialize;

import dev.cinnes.marvel.repository.CharacterRepository;
import dev.cinnes.marvel.service.MarvelApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@Slf4j
public class CharacterInitializer implements CommandLineRunner {

    @Autowired
    private MarvelApiService marvelApiService;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ReactiveRedisConnectionFactory connectionFactory;

    @Override
    public void run(String... args) {
        // TODO: possible to reduce load time? bulk save?
        connectionFactory
                .getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(marvelApiService.findAll())
                .flatMap(characterRepository::save)
                .doOnSubscribe(sub ->  log.info("Loading test data..."))
                .doOnComplete(() -> log.info("Test data loaded!"))
                .subscribe();
    }
}
