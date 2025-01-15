package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing RecipeIngredient entities.
 */
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    /**
     * Finds all ingredients for a given Recipe ID.
     *
     * @param recipeId the ID of the Recipe whose ingredients are to be found
     * @return a list of RecipeIngredients associated with the given Recipe ID
     */
    List<RecipeIngredient> findByRecipeId(Long recipeId);

    /**
     * Finds all recipes with a given Ingredient ID.
     *
     * @param ingredientId the ID of the Ingredient whose recipes are to be found
     * @return a list of RecipeIngredients associated with the given Ingredient ID
     */
    List<RecipeIngredient> findByIngredientId(Long ingredientId);
}
