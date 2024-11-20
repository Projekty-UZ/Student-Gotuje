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

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"images", "ingredients", "tags","ratings"})
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
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();

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
