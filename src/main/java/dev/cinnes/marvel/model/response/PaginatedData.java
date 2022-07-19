package dev.cinnes.marvel.model.response;

import dev.cinnes.marvel.model.MarvelCharacter;
import lombok.*;

import java.util.List;

@Data
public class PaginatedData {
    private int offset;
    private int limit;
    private int count;
    private List<MarvelCharacter> results;

    public Boolean hasMore() {
        return false;
//        return this.count == this.limit;
    }

    public int nextOffset() {
        return this.offset + limit;
    }
}