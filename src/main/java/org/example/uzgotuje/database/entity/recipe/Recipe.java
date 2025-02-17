package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a recipe.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"images", "ingredients", "tags", "ratings"})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private Double averageRating = 0.0;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private RecipeTypes type;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "recipe-image")
    private Set<Image> images = new HashSet<>(); // One-to-Many relationship with Image

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "recipe-ingredient")
    private Set<RecipeIngredient> ingredients = new HashSet<>(); // One-to-Many relationship with RecipeIngredient

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>(); // One-to-Many relationship with Rating

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "recipe-comment")
    private Set<Comment> comments = new HashSet<>(); // One-to-Many relationship with Comment

    @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags; // Many-to-Many relationship with Tag

    /**
     * Constructs a new Recipe with the specified name, description, and type.
     *
     * @param name the name of the recipe
     * @param description the description of the recipe
     * @param type the type of the recipe
     */
    public Recipe(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = RecipeTypes.valueOf(type);
    }

    /**
     * Updates the average rating of the recipe based on its ratings.
     */
    public void updateAverageRating() {
        if (ratings.isEmpty()) {
            this.averageRating = 0.0;
        } else {
            this.averageRating = ratings.stream()
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0.0);
        }
    }
}