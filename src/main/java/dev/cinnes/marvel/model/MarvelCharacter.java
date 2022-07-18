package dev.cinnes.marvel.model;

import lombok.Getter;
import lombok.Setter;

public class MarvelCharacter {

    public MarvelCharacter(String name) {
        this.name = name;
    }

    @Getter
    @Setter
    private String name;

    @Override
    public String toString() {
        return String.format("Character[%s]", name);
    }
}

