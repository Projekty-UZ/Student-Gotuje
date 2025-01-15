package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request object for creating a rating for a recipe.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRatingRequest {
    /**
     * The ID of the recipe to be rated.
     */
    private final Long recipeId;

    /**
     * The score given to the recipe.
     */
    private final int score;
}
