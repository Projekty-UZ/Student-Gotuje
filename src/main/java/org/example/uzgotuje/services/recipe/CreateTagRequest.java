package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class CreateTagRequest {
    private final String name;
    private final String tagType;
}
