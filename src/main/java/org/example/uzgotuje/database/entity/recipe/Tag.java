package org.example.uzgotuje.database.entity.recipe;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TagTypes tagType;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Recipe> recipes;

    public Tag(String tagType, String name) {
        this.tagType = TagTypes.valueOf(tagType);
        this.name = name;
    }
}
