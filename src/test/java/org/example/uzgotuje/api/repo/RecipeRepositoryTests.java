package org.example.uzgotuje.api.repo;


import org.example.uzgotuje.database.entity.recipe.Recipe;
import org.example.uzgotuje.database.repository.recipe.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecipeRepositoryTests {
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testSave(){
        // given
        Recipe recipe = new Recipe("pasta", "description", "DINNER");

        // when
        Recipe savedRecipe = recipeRepository.save(recipe);

        // then
        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getId()).isNotNull();
        assertThat(savedRecipe.getName()).isEqualTo(recipe.getName());
        assertThat(savedRecipe.getDescription()).isEqualTo(recipe.getDescription());
        assertThat(savedRecipe.getType()).isEqualTo(recipe.getType());
    }

    @Test
    public void testFindAll(){
        // given
        Recipe[] recipes = {
                new Recipe("pasta", "description", "DINNER"),
                new Recipe("pizza", "description", "DINNER"),
                new Recipe("salad", "description", "DINNER"),
                new Recipe("soup", "description", "DINNER"),
                new Recipe("sandwich", "description", "DINNER"),
                new Recipe("burger", "description", "DINNER"),
                new Recipe("cake", "description", "BREAKFAST"),
                new Recipe("pancake", "description", "BREAKFAST"),
        };
        recipeRepository.saveAll(Arrays.asList(recipes));

        // when
        List<Recipe> foundRecipes = recipeRepository.findAll();

        // then
        assertThat(foundRecipes).isNotNull();
        assertThat(foundRecipes.size()).isEqualTo(8);
    }

    @Test
    public void testFindById(){
        // given
        Recipe recipe = new Recipe("pasta", "description", "DINNER");
        Recipe savedRecipe = recipeRepository.save(recipe);

        // when
        Recipe foundRecipe = recipeRepository.findById(savedRecipe.getId()).orElse(null);

        // then
        assertThat(foundRecipe).isNotNull();
        assertThat(foundRecipe.getName()).isEqualTo(recipe.getName());
        assertThat(foundRecipe.getDescription()).isEqualTo(recipe.getDescription());
        assertThat(foundRecipe.getType()).isEqualTo(recipe.getType());
    }

    @Test
    public void testFindByName(){
        // given
        Recipe recipe = new Recipe("pasta", "description", "DINNER");
        recipeRepository.save(recipe);

        // when
        Recipe foundRecipe = recipeRepository.findByName("pasta").orElse(null);

        // then
        assertThat(foundRecipe).isNotNull();
        assertThat(foundRecipe.getName()).isEqualTo(recipe.getName());
        assertThat(foundRecipe.getDescription()).isEqualTo(recipe.getDescription());
        assertThat(foundRecipe.getType()).isEqualTo(recipe.getType());
    }

    @Test
    public void testFindByNameWithoutMatch(){
        // given
        Recipe recipe = new Recipe("pasta", "description", "DINNER");
        recipeRepository.save(recipe);

        // when
        Recipe foundRecipe = recipeRepository.findByName("pizza").orElse(null);

        // then
        assertThat(foundRecipe).isNull();
    }

    @Test
    public void testDeleteById(){
        // given
        Recipe recipe = new Recipe("pasta", "description", "DINNER");
        Recipe savedRecipe = recipeRepository.save(recipe);

        // when
        recipeRepository.deleteById(savedRecipe.getId());
        Recipe foundRecipe = recipeRepository.findById(savedRecipe.getId()).orElse(null);

        // then
        assertThat(foundRecipe).isNull();
    }

    @Test
    public void testFindingUpTo5RandomRecipesOfType(){
        // given
        Recipe[] recipes = {
                new Recipe("pasta", "description", "DINNER"),
                new Recipe("pizza", "description", "DINNER"),
                new Recipe("salad", "description", "DINNER"),
                new Recipe("soup", "description", "DINNER"),
                new Recipe("sandwich", "description", "DINNER"),
                new Recipe("burger", "description", "DINNER"),
                new Recipe("cake", "description", "BREAKFAST"),
                new Recipe("pancake", "description", "BREAKFAST"),
        };
        recipeRepository.saveAll(Arrays.asList(recipes));

        // when
        List<Recipe> foundRecipes = recipeRepository.findRandomRecipesByType("DINNER");
        List<Recipe> foundRecipes2 = recipeRepository.findRandomRecipesByType("BREAKFAST");
        List<Recipe> foundRecipes3 = recipeRepository.findRandomRecipesByType("SUPPER");

        // then
        assertThat(foundRecipes).isNotNull();
        assertThat(foundRecipes.size()).isEqualTo(5);

        assertThat(foundRecipes2).isNotNull();
        assertThat(foundRecipes2.size()).isEqualTo(2);

        assertThat(foundRecipes3).isEmpty();
    }
}
