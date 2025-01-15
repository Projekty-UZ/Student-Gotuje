package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing an image associated with a recipe.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath; // Path where the image is stored on the server

    @ManyToOne
    @JsonBackReference(value = "recipe-image")
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    /**
     * Constructs a new Image with the specified image path.
     *
     * @param imagePath the path where the image is stored on the server
     */
    public Image(String imagePath) {
        this.imagePath = imagePath;
    }
}
