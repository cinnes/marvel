package dev.cinnes.marvel.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record Thumbnail(String path, String extension) implements Serializable {
}