package dev.cinnes.marvel.model.response;

import dev.cinnes.marvel.model.MarvelCharacter;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedData {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<MarvelCharacter> results;
}
