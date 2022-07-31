package dev.cinnes.marvel.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static dev.cinnes.marvel.CharacterUtils.generateCharacter;
import static org.mockito.Mockito.*;

public class GoogleTranslateServiceTests {

    private final Translate translate = mock(Translate.class);

    private final GoogleTranslateService googleTranslateService = new GoogleTranslateService(/*translate*/);

    @Test
    public void shouldTranslate() {
        // given
        final var character = generateCharacter(123);
        final var translation = mock(Translation.class);

        when(translation.getTranslatedText()).thenReturn("translatedText");

        when(translate.translate(
                character.description(),
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("it"),
                Translate.TranslateOption.format("text")))
                .thenReturn(translation);

        // when
        final var result = googleTranslateService.translate(character, "it");

        // then
        StepVerifier
                .create(result)
                .expectNextMatches(ch ->
                        ch.id() == character.id() &&
                        ch.name().equals(character.name()) &&
                        ch.description().equals("translatedText") &&
                        ch.thumbnail().equals(character.thumbnail()))
                .verifyComplete();

        verify(translation).getTranslatedText();
        verify(translate).translate(
                character.description(),
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("it"),
                Translate.TranslateOption.format("text"));

        verifyNoMoreInteractions(translation, translate);
    }
}
