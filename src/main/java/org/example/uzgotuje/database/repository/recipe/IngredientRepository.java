package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Ingredient entities.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Finds an Ingredient by its name.
     *
     * @param name the name of the Ingredient
     * @return an Optional containing the found Ingredient, or empty if not found
     */
    Optional<Ingredient> findByName(String name);
}
