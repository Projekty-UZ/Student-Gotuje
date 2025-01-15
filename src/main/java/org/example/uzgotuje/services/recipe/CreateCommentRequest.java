package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class CreateCommentRequest {
    /**
     * The ID of the recipe to which the comment belongs.
     */
    private final Long recipeId;

    /**
     * The content of the comment.
     */
    private final String content;
}
