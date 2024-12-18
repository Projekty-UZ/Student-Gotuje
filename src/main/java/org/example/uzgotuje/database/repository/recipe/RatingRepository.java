package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.recipe.Rating;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndRecipe(User user, Recipe recipe);
    List<Rating> findAllByRecipe(Recipe recipe);
    List<Rating> findAllByUser(User user);
    void deleteByUserAndRecipe(User user, Recipe recipe);
}
