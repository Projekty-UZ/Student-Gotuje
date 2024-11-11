package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByRecipeId(Long recipeId);
}
