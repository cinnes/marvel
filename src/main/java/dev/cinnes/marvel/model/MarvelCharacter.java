package dev.cinnes.marvel.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record MarvelCharacter(int id, String name, String description, Thumbnail thumbnail) implements Serializable {
}
