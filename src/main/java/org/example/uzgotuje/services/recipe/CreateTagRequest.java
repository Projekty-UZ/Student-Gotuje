package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request object for creating a tag.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class CreateTagRequest {
    /**
     * The name of the tag.
     */
    private final String name;

    /**
     * The type of the tag.
     */
    private final String tagType;
}
