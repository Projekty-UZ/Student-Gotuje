package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Comment;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Comment entities.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds a list of Comments associated with a given Recipe.
     *
     * @param recipe the Recipe whose comments are to be found
     * @return an Optional containing the list of found Comments, or empty if none found
     */
    Optional<List<Comment>> findByRecipe(Recipe recipe);
}