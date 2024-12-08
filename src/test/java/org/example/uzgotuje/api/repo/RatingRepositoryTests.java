package org.example.uzgotuje.api.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.entity.recipe.Rating;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.example.uzgotuje.database.repository.recipe.RatingRepository;
import org.example.uzgotuje.database.repository.recipe.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RatingRepositoryTests {
    @Autowired
    RatingRepository ratingRepository;
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void userRecipeData(){
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE recipe AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE rating AUTO_INCREMENT = 1").executeUpdate();
        User testUser = new User("name","email","hashedPassword", UserRoles.USER);
        User testUser2 = new User("name2","email2","hashedPassword2", UserRoles.USER);
        entityManager.persist(testUser);
        entityManager.persist(testUser2);
        Recipe[] recipes = {
                new Recipe("pasta", "description", "DINNER"),
                new Recipe("pizza", "description", "DINNER"),
                new Recipe("salad", "description", "DINNER"),
                new Recipe("soup", "description", "DINNER"),
        };
        for (Recipe recipe : recipes) {
            entityManager.persist(recipe);
        }
        entityManager.flush();
    }

    @Test
    public void testSave(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pasta'", Recipe.class).getSingleResult();
        // given
        Rating rating = new Rating(testUser, testRecipe, 5);

        // when
        Rating savedRating = ratingRepository.save(rating);

        // then
        assertThat(savedRating).isNotNull();
    }

    @Test
    public void testFindByUserAndRecipe(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pasta'", Recipe.class).getSingleResult();
        Recipe testRecipe2 = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pizza'", Recipe.class).getSingleResult();
        // given
        Rating rating = new Rating(testUser, testRecipe, 5);

        ratingRepository.save(rating);

        // when
        Rating foundRating = ratingRepository.findByUserAndRecipe(testUser, testRecipe).orElse(null);
        Rating notFoundRating = ratingRepository.findByUserAndRecipe(testUser, testRecipe2).orElse(null);

        // then
        assertThat(foundRating).isNotNull();

        assertThat(notFoundRating).isNull();
    }
}
