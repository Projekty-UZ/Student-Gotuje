package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.uzgotuje.database.entity.recipe.Ingredient;
import org.example.uzgotuje.database.entity.recipe.RecipeIngredient;
import org.example.uzgotuje.database.entity.recipe.Tag;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request object for creating a recipe.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRecipeRequest {
    /**
     * The name of the recipe.
     */
    private final String name;

    /**
     * The description of the recipe.
     */
    private final String description;

    /**
     * The type of the recipe (e.g., dessert, main course).
     */
    private final String type;

    /**
     * The images associated with the recipe.
     */
    private final MultipartFile[] images;

    /**
     * The tags associated with the recipe.
     */
    private final Tag[] tags;

    /**
     * The ingredients used in the recipe.
     */
    private final RecipeIngredient[] ingredients;
}
