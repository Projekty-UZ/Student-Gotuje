package org.example.uzgotuje.services.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response object for token-related operations.
 */
@Getter
@AllArgsConstructor
public class TokenResponse {
    /**
     * The message associated with the token response.
     */
    private final String message;
}
