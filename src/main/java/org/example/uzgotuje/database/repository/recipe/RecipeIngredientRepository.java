package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    //find all ingredients for recipe
    List<RecipeIngredient> findByRecipeId(Long recipeId);
    //find all recipes with ingredient
    List<RecipeIngredient> findByIngredientId(Long ingredientId);
}
