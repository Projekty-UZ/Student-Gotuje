package org.example.uzgotuje.database.entity.recipe;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"images", "ingredients", "tags"})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private RecipeTypes type;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>(); // One-to-Many relationship with Image

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Recipe(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = RecipeTypes.valueOf(type);
    }

}
