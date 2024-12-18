package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    //find ingredient by name
    Optional<Ingredient> findByName(String name);
}
