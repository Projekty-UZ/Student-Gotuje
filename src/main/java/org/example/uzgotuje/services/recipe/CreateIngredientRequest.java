package org.example.uzgotuje.services.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class CreateIngredientRequest {
    private final String name;
    @JsonCreator
    public CreateIngredientRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
