package org.example.uzgotuje.api.repo;


import org.example.uzgotuje.database.entity.recipe.Ingredient;
import org.example.uzgotuje.database.repository.recipe.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IngredientRepositoryTests {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testSave(){
        // given
        Ingredient ingredient = new Ingredient("pasta");

        // when
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        // then
        assertThat(savedIngredient).isNotNull();
        assertThat(savedIngredient.getId()).isNotNull();
        assertThat(savedIngredient.getName()).isEqualTo(ingredient.getName());
    }

    @Test
    public void testFindAll(){
        // given
        Ingredient[] ingredients = {
                new Ingredient("makaron"),
                new Ingredient("ser"),
                new Ingredient("pomidor"),
                new Ingredient("cebula"),
                new Ingredient("czosnek"),
                new Ingredient("oliwa"),
                new Ingredient("jajko"),
                new Ingredient("mąka")
        };
        ingredientRepository.saveAll(Arrays.asList(ingredients));

        // when
        List<Ingredient> foundIngredients = ingredientRepository.findAll();

        // then
        assertThat(foundIngredients).isNotNull();
        assertThat(foundIngredients.size()).isEqualTo(8);
    }

    @Test
    public void testFindByName(){
        // given
        Ingredient ingredient = new Ingredient("makaron");
        ingredientRepository.save(ingredient);

        // when
        Ingredient foundIngredient = ingredientRepository.findByName("makaron").orElse(null);

        // then
        assertThat(foundIngredient).isNotNull();
        assertThat(foundIngredient.getName()).isEqualTo(ingredient.getName());
    }

    @Test
    public void testFindByNameWithoutMatch(){
        // given
        Ingredient ingredient = new Ingredient("makaron");
        ingredientRepository.save(ingredient);

        // when
        Ingredient foundIngredient = ingredientRepository.findByName("ser").orElse(null);

        // then
        assertThat(foundIngredient).isNull();
    }

    @Test
    public void testFindById(){
        // given
        Ingredient ingredient = new Ingredient("makaron");
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        // when
        Ingredient foundIngredient = ingredientRepository.findById(savedIngredient.getId()).orElse(null);

        // then
        assertThat(foundIngredient).isNotNull();
        assertThat(foundIngredient.getName()).isEqualTo(ingredient.getName());
    }

    @Test
    public void testFindByIdWithoutMatch(){
        // given
        Ingredient ingredient = new Ingredient("makaron");
        ingredientRepository.save(ingredient);

        // when
        Ingredient foundIngredient = ingredientRepository.findById(100L).orElse(null);

        // then
        assertThat(foundIngredient).isNull();
    }

    @Test
    public void testDelete(){
        // given
        Ingredient ingredient = new Ingredient("makaron");
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        // when
        ingredientRepository.delete(savedIngredient);

        // then
        assertThat(ingredientRepository.findById(savedIngredient.getId())).isEmpty();
    }
}
