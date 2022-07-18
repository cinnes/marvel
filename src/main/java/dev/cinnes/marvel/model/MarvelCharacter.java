package dev.cinnes.marvel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("characters")
public class MarvelCharacter implements Persistable<Integer> {
    @Id
    @NonNull
    private Integer id;
    private String name;
    private String description;

    // always overwrite
    @Override
    @Transient
    public boolean isNew() {
        return true;
    }
}

