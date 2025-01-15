package org.example.uzgotuje.services.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationResponse {
    /**
     * The message indicating the result of the registration.
     */
    private final String message;

    /**
     * The token generated upon successful registration.
     */
    private final String token;
}
