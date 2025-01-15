package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing the relationship between a recipe and an ingredient,
 * including the quantity and type of the ingredient used in the recipe.
 */
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
    private Recipe recipe; // Many-to-One relationship with Recipe

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient; // Many-to-One relationship with Ingredient

    private String quantity;     // Quantity of the ingredient used in the recipe
    private String quantityType; // Type of the quantity (e.g., grams, cups)

    /**
     * Constructs a new RecipeIngredient with the specified recipe, ingredient, quantity, and quantity type.
     *
     * @param recipe the recipe that uses the ingredient
     * @param ingredient the ingredient used in the recipe
     * @param quantity the quantity of the ingredient used
     * @param quantityType the type of the quantity (e.g., grams, cups)
     */
    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String quantity, String quantityType) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.quantityType = quantityType;
    }
}