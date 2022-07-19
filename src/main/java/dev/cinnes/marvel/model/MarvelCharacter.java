package dev.cinnes.marvel.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarvelCharacter implements Serializable {
    private Integer id;
    private String name;
    private String description;
}

