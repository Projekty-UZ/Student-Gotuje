package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.recipe.Favorite;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Favorite entities.
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Finds a Favorite by the given User and Recipe.
     *
     * @param user the User associated with the Favorite
     * @param recipe the Recipe associated with the Favorite
     * @return an Optional containing the found Favorite, or empty if not found
     */
    Optional<Favorite> findByUserAndRecipe(User user, Recipe recipe);

    /**
     * Finds all Favorites associated with a given User.
     *
     * @param user the User whose Favorites are to be found
     * @return a list of Favorites associated with the given User
     */
    List<Favorite> findAllByUser(User user);

    /**
     * Deletes a Favorite by the given User and Recipe.
     *
     * @param user the User associated with the Favorite
     * @param recipe the Recipe associated with the Favorite
     */
    void deleteByUserAndRecipe(User user, Recipe recipe);
}
