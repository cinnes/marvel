package dev.cinnes.marvel.model.response;

import dev.cinnes.marvel.model.MarvelCharacter;

import java.util.List;

public record PaginatedData(int count, int total, List<MarvelCharacter> results) {
}