package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Image entities.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Finds a list of Images associated with a given Recipe ID.
     *
     * @param recipeId the ID of the Recipe whose images are to be found
     * @return a list of Images associated with the given Recipe ID
     */
    List<Image> findByRecipeId(Long recipeId);
}
