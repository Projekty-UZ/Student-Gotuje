package org.example.uzgotuje.api.repo;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.entity.recipe.Favorite;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.example.uzgotuje.database.repository.recipe.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FavoriteRepositoryTests {
    @Autowired
    FavoriteRepository favoriteRepository;
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void userRecipeData(){
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE recipe AUTO_INCREMENT = 1").executeUpdate();
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
        Favorite favorite = new Favorite(testUser, testRecipe);

        // when
        Favorite savedFavorite = favoriteRepository.save(favorite);

        // then
        assertThat(savedFavorite).isNotNull();
        assertThat(savedFavorite.getId()).isNotNull();
        assertThat(savedFavorite.getUser()).isEqualTo(favorite.getUser());
        assertThat(savedFavorite.getRecipe()).isEqualTo(favorite.getRecipe());
    }

    @Test
    public void testFindByUserAndRecipe(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pasta'", Recipe.class).getSingleResult();
        Recipe testRecipe2 = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pizza'", Recipe.class).getSingleResult();
        // given
        Favorite favorite = new Favorite(testUser, testRecipe);
        favoriteRepository.save(favorite);

        // when
        Favorite foundFavorite = favoriteRepository.findByUserAndRecipe(testUser, testRecipe).orElse(null);
        Favorite foundFavorite2 = favoriteRepository.findByUserAndRecipe(testUser, testRecipe2).orElse(null);

        // then
        assertThat(foundFavorite).isNotNull();
        assertThat(foundFavorite.getUser()).isEqualTo(favorite.getUser());
        assertThat(foundFavorite.getRecipe()).isEqualTo(favorite.getRecipe());

        assertThat(foundFavorite2).isNull();
    }

    @Test
    public void testFindAllByUser(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        User testUser2 = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email2'", User.class).getSingleResult();
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pasta'", Recipe.class).getSingleResult();
        Recipe testRecipe2 = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pizza'", Recipe.class).getSingleResult();

        Favorite favorite = new Favorite(testUser, testRecipe);
        Favorite favorite2 = new Favorite(testUser, testRecipe2);
        favoriteRepository.save(favorite);
        favoriteRepository.save(favorite2);

        List<Favorite> favorites1 = favoriteRepository.findAllByUser(testUser);
        List<Favorite> favorites2 = favoriteRepository.findAllByUser(testUser2);

        assertThat(favorites1).isNotNull();
        assertThat(favorites1.size()).isEqualTo(2);

        assertThat(favorites2).isEmpty();

        Favorite favorite3 = new Favorite(testUser2, testRecipe);
        favoriteRepository.save(favorite3);

        List<Favorite> favorites3 = favoriteRepository.findAllByUser(testUser2);

        assertThat(favorites3).isNotNull();
        assertThat(favorites3.size()).isEqualTo(1);
    }

    @Test
    public void testDelete(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = 'pasta'", Recipe.class).getSingleResult();
        // given
        Favorite favorite = new Favorite(testUser, testRecipe);
        favoriteRepository.save(favorite);

        Favorite favoriteToDelete = favoriteRepository.findByUserAndRecipe(testUser, testRecipe).orElse(null);
        // when
        assert favoriteToDelete != null;
        favoriteRepository.delete(favoriteToDelete);

        // then
        assertThat(favoriteRepository.findByUserAndRecipe(testUser, testRecipe)).isEmpty();
    }
}
