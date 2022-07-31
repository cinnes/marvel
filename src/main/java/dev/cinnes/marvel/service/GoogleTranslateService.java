package dev.cinnes.marvel.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import dev.cinnes.marvel.Constants;
import dev.cinnes.marvel.model.MarvelCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleTranslateService {

//    private final Translate translate;

    /**
     * Translates a Marvel character from English (en) to a given language. Currently translates description only as
     * per spec. This method is immutable and will return a new instance.
     *
     * @param character Marvel character to obtain a translation for
     * @param language language to convert the character into
     * @return translated character (new instance)
     */
    public Mono<MarvelCharacter> translate(MarvelCharacter character, String language) {
        return Mono
                .just(TranslateOptions.getDefaultInstance().getService())
                .map(client -> {
                    final var translation =
                            client.translate(
                                    character.description(),
                                    Translate.TranslateOption.sourceLanguage(Constants.DEFAULT_LANGUAGE),
                                    Translate.TranslateOption.targetLanguage(language),
                                    Translate.TranslateOption.format("text")); // default is html encoded

                    return MarvelCharacter
                            .builder()
                            .id(character.id())
                            .name(character.name())
                            .description(translation.getTranslatedText())
                            .thumbnail(character.thumbnail())
                            .build();
                });
    }
}
