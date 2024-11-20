package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe", "ingredient"})
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference(value = "recipe-ingredient")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private String quantity;     // Additional column
    private String quantityType; // Additional column

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String quantity, String quantityType) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.quantityType = quantityType;
    }

}
