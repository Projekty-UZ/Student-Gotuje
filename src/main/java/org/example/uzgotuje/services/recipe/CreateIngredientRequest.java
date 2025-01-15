package org.example.uzgotuje.services.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
/**
 * Request object for creating an ingredient.
 */
@Getter
@EqualsAndHashCode
@ToString
public class CreateIngredientRequest {
    /**
     * The name of the ingredient.
     */
    private final String name;

    /**
     * Constructs a new CreateIngredientRequest with the specified ingredient name.
     *
     * @param name the name of the ingredient
     */
    @JsonCreator
    public CreateIngredientRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
