package org.example.uzgotuje.services.recipe;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.uzgotuje.database.entity.recipe.Tag;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecipeSearchRequest {
    private final Tag[] tags;
    private final String type;
    private final String name;
    private final boolean sortByRatingDesc;
}
