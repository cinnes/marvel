package dev.cinnes.marvel.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCharactersResponse {
    private PaginatedData data;
}
