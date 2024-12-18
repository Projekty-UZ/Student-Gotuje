package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.recipe.Favorite;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserAndRecipe(User user, Recipe recipe);
    List<Favorite> findAllByUser(User user);
    void deleteByUserAndRecipe(User user, Recipe recipe);
}
