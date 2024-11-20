package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.example.uzgotuje.database.entity.recipe.RecipeTypes;
import org.example.uzgotuje.database.entity.recipe.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    //find recipe with Tag
    List<Recipe> findByTags_Name(String tagName);
    //find recipe with certain type of recipe dinner, breakfast, etc.
    List<Recipe> findByType(RecipeTypes type);

    Optional<Recipe> findByName(String name);

    void deleteById(Long id);
}

