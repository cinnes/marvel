package dev.cinnes.marvel.model.response;

import dev.cinnes.marvel.model.MarvelCharacter;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedData {
    private int offset;
    private int limit;
    private int count;
    private List<MarvelCharacter> results;

    @Transient
    public Boolean hasMore() {
        return this.count == this.limit;
    }

    public String nextPage() {
        return "/characters?offset=" + (this.offset + limit);
    }
}