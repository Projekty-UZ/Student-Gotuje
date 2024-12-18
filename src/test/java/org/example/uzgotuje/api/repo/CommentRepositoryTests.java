package org.example.uzgotuje.api.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.recipe.Comment;
import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.example.uzgotuje.database.repository.recipe.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTests {
    @Autowired
    CommentRepository commentRepository;
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void recipeData(){
        entityManager.createNativeQuery("ALTER TABLE recipe AUTO_INCREMENT = 1").executeUpdate();

        Recipe[] recipes ={
                new Recipe("pasta", "description", "DINNER"),
                new Recipe("pizza", "description", "DINNER"),
                new Recipe("salad", "description", "DINNER"),
        };

        for (Recipe recipe : recipes) {
            entityManager.persist(recipe);
        }

        entityManager.flush();
    }

    @Test
    public void testSave(){
        // given
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.id = 1", Recipe.class).getSingleResult();
        Comment comment = new Comment("comment", "user");
        comment.setRecipe(testRecipe);

        // when
        Comment savedComment = commentRepository.save(comment);

        // then
        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getRecipe()).isEqualTo(testRecipe);

    }

    @Test
    public void testFindByRecipe(){
        // given
        Recipe testRecipe = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.id = 1", Recipe.class).getSingleResult();
        Recipe testRecipe2 = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.id = 2", Recipe.class).getSingleResult();
        Recipe testRecipe3 = entityManager.createQuery("SELECT r FROM Recipe r WHERE r.id = 3", Recipe.class).getSingleResult();
        Comment comment1 = new Comment("comment", "user");
        Comment comment2 = new Comment("comment", "user");
        Comment comment3 = new Comment("comment", "user");

        comment1.setRecipe(testRecipe);
        comment2.setRecipe(testRecipe);
        comment3.setRecipe(testRecipe2);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        // when
        List<Comment> foundComment1 = commentRepository.findByRecipe(testRecipe).orElse(Collections.emptyList());
        List<Comment> foundComment2 = commentRepository.findByRecipe(testRecipe2).orElse(Collections.emptyList());
        List<Comment> foundComment3 = commentRepository.findByRecipe(testRecipe3).orElse(Collections.emptyList());

        // then
        assertThat(foundComment1).isNotEmpty();
        assertThat(foundComment1.size()).isEqualTo(2);

        assertThat(foundComment2).isNotEmpty();
        assertThat(foundComment2.size()).isEqualTo(1);

        assertThat(foundComment3).isEmpty();
    }
}
