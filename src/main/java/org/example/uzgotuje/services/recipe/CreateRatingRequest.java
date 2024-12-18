package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRatingRequest {
    private final Long recipeId;
    private final int score;
}
