package org.example.uzgotuje.services.authorization;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    /**
     * The username of the user registering.
     */
    private final String username;

    /**
     * The email of the user registering.
     */
    private final String email;

    /**
     * The password of the user registering.
     */
    private final String password;

    /**
     * The repeated password for confirmation.
     */
    private final String repeatPassword;
}
