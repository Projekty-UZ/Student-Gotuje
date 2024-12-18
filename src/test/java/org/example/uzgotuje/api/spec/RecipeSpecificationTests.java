package org.example.uzgotuje.api.spec;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.uzgotuje.database.entity.recipe.*;
import org.example.uzgotuje.database.repository.recipe.RecipeRepository;
import org.example.uzgotuje.database.repository.recipe.RecipeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@org.junit.jupiter.api.Tag("slow")
public class RecipeSpecificationTests {
    @Autowired
    private RecipeRepository recipeRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager.createNativeQuery("ALTER TABLE recipe AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE tag AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE ingredient AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE recipe_ingredient AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE recipe_tag AUTO_INCREMENT = 1").executeUpdate();

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

        Tag[] tags = {
                new Tag(TagTypes.CUISINE.toString(),"meksykańska"),
                new Tag(TagTypes.CUISINE.toString(),"włoska"),
                new Tag(TagTypes.CUISINE.toString(),"polska"),
                new Tag(TagTypes.CUISINE.toString(),"francuska"),
                new Tag(TagTypes.CUISINE.toString(),"chińska"),
                new Tag(TagTypes.CUISINE.toString(),"japońska"),
                new Tag(TagTypes.DIET.toString(),"wegetariańska"),
                new Tag(TagTypes.DIET.toString(),"wegańska"),
                new Tag(TagTypes.DIET.toString(),"bezglutenowa"),
                new Tag(TagTypes.DIET.toString(),"bez laktozy"),
        };

        Ingredient[] ingredients = {
                new Ingredient("makaron"),
                new Ingredient("ser"),
                new Ingredient("pomidor"),
                new Ingredient("mieso"),
                new Ingredient("salata"),
                new Ingredient("chleb"),
                new Ingredient("jajko"),
                new Ingredient("marchewka"),
        };


        for(Ingredient ingredient : ingredients){
            entityManager.persist(ingredient);
        }

        for(Tag tag : tags){
            entityManager.persist(tag);
        }

        recipes[0].setTags(new HashSet<>(Arrays.asList(tags[0], tags[9])));
        recipes[1].setTags(new HashSet<>(Arrays.asList(tags[1], tags[8])));
        recipes[2].setTags(new HashSet<>(Arrays.asList(tags[2], tags[7])));
        recipes[3].setTags(new HashSet<>(Arrays.asList(tags[3], tags[6])));
        recipes[4].setTags(new HashSet<>(List.of(tags[4])));
        recipes[5].setTags(new HashSet<>(List.of(tags[5])));
        recipes[6].setTags(new HashSet<>(List.of(tags[6])));
        recipes[7].setTags(new HashSet<>(List.of(tags[7])));

        RecipeIngredient[] recipeIngredients = {
                new RecipeIngredient(recipes[0], ingredients[0], "100", "g"),
                new RecipeIngredient(recipes[0], ingredients[1], "50", "g"),
                new RecipeIngredient(recipes[0], ingredients[2], "100", "g"),
                new RecipeIngredient(recipes[1], ingredients[0], "100", "g"),
                new RecipeIngredient(recipes[1], ingredients[1], "50", "g"),
                new RecipeIngredient(recipes[1], ingredients[2], "100", "g"),
                new RecipeIngredient(recipes[2], ingredients[3], "100", "g"),
                new RecipeIngredient(recipes[2], ingredients[4], "50", "g"),
                new RecipeIngredient(recipes[2], ingredients[5], "100", "g"),
                new RecipeIngredient(recipes[3], ingredients[6], "100", "g"),
                new RecipeIngredient(recipes[3], ingredients[7], "50", "g"),
                new RecipeIngredient(recipes[3], ingredients[5], "100", "g"),
                new RecipeIngredient(recipes[4], ingredients[0], "100", "g"),
                new RecipeIngredient(recipes[4], ingredients[1], "50", "g"),
                new RecipeIngredient(recipes[4], ingredients[2], "100", "g"),
                new RecipeIngredient(recipes[5], ingredients[3], "100", "g"),
                new RecipeIngredient(recipes[5], ingredients[4], "50", "g"),
                new RecipeIngredient(recipes[5], ingredients[5], "100", "g"),
                new RecipeIngredient(recipes[6], ingredients[6], "100", "g"),
                new RecipeIngredient(recipes[6], ingredients[7], "50", "g"),
                new RecipeIngredient(recipes[6], ingredients[5], "100", "g"),
                new RecipeIngredient(recipes[7], ingredients[0], "100", "g"),
                new RecipeIngredient(recipes[7], ingredients[1], "50", "g")
        };

        for(RecipeIngredient recipeIngredient : recipeIngredients){
            entityManager.persist(recipeIngredient);
        }

        recipes[0].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[0], recipeIngredients[1], recipeIngredients[2])));
        recipes[1].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[3], recipeIngredients[4], recipeIngredients[5])));
        recipes[2].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[6], recipeIngredients[7], recipeIngredients[8])));
        recipes[3].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[9], recipeIngredients[10], recipeIngredients[11])));
        recipes[4].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[12], recipeIngredients[13], recipeIngredients[14])));
        recipes[5].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[15], recipeIngredients[16], recipeIngredients[17])));
        recipes[6].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[18], recipeIngredients[19], recipeIngredients[20])));
        recipes[7].setIngredients(new HashSet<>(Arrays.asList(recipeIngredients[21], recipeIngredients[22])));

        for(Recipe recipe : recipes){
            entityManager.persist(recipe);
        }

        entityManager.flush();
    }

    @Test
    void testReturningByName() {
        // when
        List<Recipe> foundRecipes = recipeRepository.findAll(RecipeSpecification.hasName("pasta"));
        List<Recipe> foundRecipes2 = recipeRepository.findAll(RecipeSpecification.hasName(""));

        // then
        assertThat(foundRecipes).isNotEmpty();
        assertThat(foundRecipes.size()).isEqualTo(1);
        assertThat(foundRecipes.get(0).getName()).isEqualTo("pasta");

        assertThat(foundRecipes2).isNotEmpty();
        assertThat(foundRecipes2.size()).isEqualTo(8);
    }

    @Test
    void testReturningByNameWithoutMatch() {
        // when
        List<Recipe> foundRecipes = recipeRepository.findAll(RecipeSpecification.hasName("spaghetti"));

        // then
        assertThat(foundRecipes).isEmpty();
    }

    @Test
    void testReturningByIngredientName() {
        // when
        List<Recipe> foundRecipes1 = recipeRepository.findAll(RecipeSpecification.hasName("makaron"));
        List<Recipe> foundRecipes2 = recipeRepository.findAll(RecipeSpecification.hasName("ser"));
        List<Recipe> foundRecipes3 = recipeRepository.findAll(RecipeSpecification.hasName("pomidor"));
        List<Recipe> foundRecipes4 = recipeRepository.findAll(RecipeSpecification.hasName("mieso"));
        List<Recipe> foundRecipes5 = recipeRepository.findAll(RecipeSpecification.hasName("salata"));
        List<Recipe> foundRecipes6 = recipeRepository.findAll(RecipeSpecification.hasName("chleb"));
        List<Recipe> foundRecipes7 = recipeRepository.findAll(RecipeSpecification.hasName("jajko"));
        List<Recipe> foundRecipes8 = recipeRepository.findAll(RecipeSpecification.hasName("marchewka"));
        List<Recipe> foundRecipes9 = recipeRepository.findAll(RecipeSpecification.hasName("cebula"));

        // then
        assertThat(foundRecipes1).isNotEmpty();
        assertThat(foundRecipes1.size()).isEqualTo(4);

        assertThat(foundRecipes2).isNotEmpty();
        assertThat(foundRecipes2.size()).isEqualTo(4);

        assertThat(foundRecipes3).isNotEmpty();
        assertThat(foundRecipes3.size()).isEqualTo(3);

        assertThat(foundRecipes4).isNotEmpty();
        assertThat(foundRecipes4.size()).isEqualTo(2);

        assertThat(foundRecipes5).isNotEmpty();
        assertThat(foundRecipes5.size()).isEqualTo(2);

        assertThat(foundRecipes6).isNotEmpty();
        assertThat(foundRecipes6.size()).isEqualTo(4);

        assertThat(foundRecipes7).isNotEmpty();
        assertThat(foundRecipes7.size()).isEqualTo(2);

        assertThat(foundRecipes8).isNotEmpty();
        assertThat(foundRecipes8.size()).isEqualTo(2);

        assertThat(foundRecipes9).isEmpty();
    }
    @Test
    void testReturningByProvidedTags() {

        List<Tag> dbTags = entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        // when
        List<Recipe> foundRecipes1 = recipeRepository.findAll(RecipeSpecification.hasTags(new HashSet<>(List.of(dbTags.get(7)))));
        List<Recipe> foundRecipes2 = recipeRepository.findAll(RecipeSpecification.hasTags(new HashSet<>(List.of(dbTags.get(0)))));
        List<Recipe> foundRecipes3 = recipeRepository.findAll(RecipeSpecification.hasTags(new HashSet<>(List.of(
                                                                                                                dbTags.get(0),
                                                                                                                dbTags.get(9)
                                                                                                                ))));
        List<Recipe> foundRecipes4 = recipeRepository.findAll(RecipeSpecification.hasTags(new HashSet<>(List.of(
                                                                                                                dbTags.get(0),
                                                                                                                dbTags.get(1)
                                                                                                                ))));
        List<Recipe> foundRecipes5 = recipeRepository.findAll(RecipeSpecification.hasTags(new HashSet<>()));

        assertThat(foundRecipes1).isNotEmpty();
        assertThat(foundRecipes1.size()).isEqualTo(2);

        assertThat(foundRecipes2).isNotEmpty();
        assertThat(foundRecipes2.size()).isEqualTo(1);

        assertThat(foundRecipes3).isNotEmpty();
        assertThat(foundRecipes3.size()).isEqualTo(1);

        assertThat(foundRecipes4).isNotEmpty();
        assertThat(foundRecipes4.size()).isEqualTo(2);

        assertThat(foundRecipes5).isNotEmpty();
        assertThat(foundRecipes5.size()).isEqualTo(8);
    }

    @Test
    void testReturningByType() {
        // when
        List<Recipe> foundRecipes1 = recipeRepository.findAll(RecipeSpecification.hasType(RecipeTypes.DINNER));
        List<Recipe> foundRecipes2 = recipeRepository.findAll(RecipeSpecification.hasType(RecipeTypes.BREAKFAST));
        List<Recipe> foundRecipes3 = recipeRepository.findAll(RecipeSpecification.hasType(RecipeTypes.SUPPER));

        // then
        assertThat(foundRecipes1).isNotEmpty();
        assertThat(foundRecipes1.size()).isEqualTo(6);

        assertThat(foundRecipes2).isNotEmpty();
        assertThat(foundRecipes2.size()).isEqualTo(2);

        assertThat(foundRecipes3).isEmpty();
    }

    @Test
    void testReturningByCombination(){
        List<Tag> dbTags = entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();

        Specification<Recipe> recipeSpecification1 = Specification.where(RecipeSpecification.hasName("pasta"))
                .and(RecipeSpecification.hasTags(new HashSet<>(List.of(dbTags.get(0)))))
                .and(RecipeSpecification.hasType(RecipeTypes.DINNER));

        Specification<Recipe> recipeSpecification2 = Specification.where(RecipeSpecification.hasName("pasta"))
                .and(RecipeSpecification.hasTags(new HashSet<>(List.of(dbTags.get(0))))
                .and(RecipeSpecification.hasType(RecipeTypes.BREAKFAST)));

        Specification<Recipe> recipeSpecification3 = Specification.where(RecipeSpecification.hasTags(new HashSet<>(List.of(dbTags.get(0), dbTags.get(7))))
                .and(RecipeSpecification.hasType(RecipeTypes.DINNER)));

        Specification<Recipe> recipeSpecification4 = Specification.where(RecipeSpecification.hasName(""))
                .and(RecipeSpecification.hasTags(new HashSet<>())
                .and(RecipeSpecification.hasType(null)));

        List<Recipe> foundRecipes1 = recipeRepository.findAll(recipeSpecification1);
        List<Recipe> foundRecipes2 = recipeRepository.findAll(recipeSpecification2);
        List<Recipe> foundRecipes3 = recipeRepository.findAll(recipeSpecification3);
        List<Recipe> foundRecipes4 = recipeRepository.findAll(recipeSpecification4);

        assertThat(foundRecipes1).isNotEmpty();
        assertThat(foundRecipes1.size()).isEqualTo(1);

        assertThat(foundRecipes2).isEmpty();

        assertThat(foundRecipes3).isNotEmpty();
        assertThat(foundRecipes3.size()).isEqualTo(2);

        assertThat(foundRecipes4).isNotEmpty();
        assertThat(foundRecipes4.size()).isEqualTo(8);
    }
}
