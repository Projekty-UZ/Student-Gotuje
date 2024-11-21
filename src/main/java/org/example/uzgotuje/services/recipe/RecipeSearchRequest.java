package org.example.uzgotuje.services.recipe;


import lombok.*;
import org.example.uzgotuje.database.entity.recipe.Tag;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RecipeSearchRequest {
    private Tag[] tags;
    private final String type;
    private final String name;
    private final boolean sortByRatingDesc;


}
