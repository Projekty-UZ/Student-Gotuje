package org.example.uzgotuje.services.recipe;


import lombok.*;
import org.example.uzgotuje.database.entity.recipe.Tag;

import java.util.List;

/**
 * Request object for searching recipes.
 */
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecipeSearchRequest {
    /**
     * The tags associated with the recipes to search for.
     */
    private Tag[] tags;

    /**
     * The type of the recipes to search for (e.g., dessert, main course).
     */
    private final String type;

    /**
     * The name of the recipes to search for.
     */
    private final String name;

    /**
     * Whether to sort the search results by rating in descending order.
     */
    private final boolean sortByRatingDesc;
}
