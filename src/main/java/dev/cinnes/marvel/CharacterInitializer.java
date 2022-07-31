package dev.cinnes.marvel;

import dev.cinnes.marvel.utils.Constants;
import dev.cinnes.marvel.repository.CharacterRepository;
import dev.cinnes.marvel.service.MarvelApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@Slf4j
@RequiredArgsConstructor
public class CharacterInitializer implements ApplicationRunner {

    private final MarvelApiService marvelApiService;

    private final CharacterRepository characterRepository;

    private final ReactiveRedisConnectionFactory connectionFactory;

    @Value("${app.initialize-data}")
    private boolean initializeData;

    @Override
    public void run(ApplicationArguments args) {
        if (initializeData) {
            connectionFactory
                    .getReactiveConnection()
                    .serverCommands()
                    .flushAll()
                    .thenMany(marvelApiService.findAll())
                    .flatMap(c -> characterRepository.save(c, Constants.DEFAULT_LANGUAGE))
                    .doOnSubscribe(sub ->  log.info("Loading test data..."))
                    .doOnComplete(() -> log.info("Test data loaded!"))
                    .subscribe();
        }
    }
}