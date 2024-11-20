package org.example.uzgotuje.services.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class CreateFavoriteRequest {
    private final Long recipeId;
    @JsonCreator
    public CreateFavoriteRequest(@JsonProperty("recipeId") Long recipeId) {
        this.recipeId = recipeId;
    }
}
