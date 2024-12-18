package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    Optional<Recipe> findByName(String name);

    void deleteById(Long id);

    @Query(value = "SELECT * FROM recipe WHERE type = :type ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Recipe> findRandomRecipesByType(@Param("type") String type);
}

