package dev.cinnes.marvel.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCharactersResponse {
    private PaginatedData data;
}
