package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Recipe entities.
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    /**
     * Finds a Recipe by its name.
     *
     * @param name the name of the Recipe
     * @return an Optional containing the found Recipe, or empty if not found
     */
    Optional<Recipe> findByName(String name);

    /**
     * Deletes a Recipe by its ID.
     *
     * @param id the ID of the Recipe to be deleted
     */
    void deleteById(Long id);

    /**
     * Finds a list of random Recipes by type.
     *
     * @param type the type of the Recipes to be found
     * @return a list of random Recipes of the given type
     */
    @Query(value = "SELECT * FROM recipe WHERE type = :type ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Recipe> findRandomRecipesByType(@Param("type") String type);
}

