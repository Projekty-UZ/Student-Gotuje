package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Entity representing a tag that can be associated with recipes.
 */
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
    private TagTypes tagType; // Type of the tag

    private String name; // Name of the tag

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private Set<Recipe> recipes; // Recipes associated with this tag

    /**
     * Constructs a new Tag with the specified tag type and name.
     *
     * @param tagType the type of the tag
     * @param name the name of the tag
     */
    public Tag(String tagType, String name) {
        this.tagType = TagTypes.valueOf(tagType);
        this.name = name;
    }
}
