package dev.cinnes.marvel;

import dev.cinnes.marvel.model.MarvelCharacter;
import dev.cinnes.marvel.model.Thumbnail;

public class CharacterUtils {
    private static final Thumbnail thumbnail =
            Thumbnail
                    .builder()
                    .path("path")
                    .extension("ext")
                    .build();

    public static MarvelCharacter generateCharacter(final int id) {
        return MarvelCharacter
                .builder()
                .id(id)
                .name("char" + id)
                .description("char" + id + "desc")
                .thumbnail(thumbnail)
                .build();
    }
}
