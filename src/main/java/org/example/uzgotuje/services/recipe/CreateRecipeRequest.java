package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.uzgotuje.database.entity.recipe.Ingredient;
import org.example.uzgotuje.database.entity.recipe.RecipeIngredient;
import org.example.uzgotuje.database.entity.recipe.Tag;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRecipeRequest {
    private final String name;
    private final String description;
    private final String type;
    private final MultipartFile[] images;
    private final Tag[] tags;
    private final RecipeIngredient[] ingredients;
}
