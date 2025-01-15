package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.recipe.Rating;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Rating entities.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Finds a Rating by the given User and Recipe.
     *
     * @param user the User associated with the Rating
     * @param recipe the Recipe associated with the Rating
     * @return an Optional containing the found Rating, or empty if not found
     */
    Optional<Rating> findByUserAndRecipe(User user, Recipe recipe);

    /**
     * Finds all Ratings associated with a given Recipe.
     *
     * @param recipe the Recipe whose Ratings are to be found
     * @return a list of Ratings associated with the given Recipe
     */
    List<Rating> findAllByRecipe(Recipe recipe);

    /**
     * Finds all Ratings associated with a given User.
     *
     * @param user the User whose Ratings are to be found
     * @return a list of Ratings associated with the given User
     */
    List<Rating> findAllByUser(User user);

    /**
     * Deletes a Rating by the given User and Recipe.
     *
     * @param user the User associated with the Rating
     * @param recipe the Recipe associated with the Rating
     */
    void deleteByUserAndRecipe(User user, Recipe recipe);
}
