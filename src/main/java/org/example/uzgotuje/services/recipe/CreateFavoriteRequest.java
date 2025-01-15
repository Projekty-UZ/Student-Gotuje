package org.example.uzgotuje.services.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request object for creating a favorite recipe.
 */
@Getter
@EqualsAndHashCode
@ToString
public class CreateFavoriteRequest {
    /**
     * The ID of the recipe to be marked as favorite.
     */
    private final Long recipeId;

    /**
     * Constructs a new CreateFavoriteRequest with the specified recipe ID.
     *
     * @param recipeId the ID of the recipe to be marked as favorite
     */
    @JsonCreator
    public CreateFavoriteRequest(@JsonProperty("recipeId") Long recipeId) {
        this.recipeId = recipeId;
    }
}
