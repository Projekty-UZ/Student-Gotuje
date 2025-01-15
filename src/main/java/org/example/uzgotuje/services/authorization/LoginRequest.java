package org.example.uzgotuje.services.authorization;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {
    /**
     * The email of the user attempting to log in.
     */
    private final String email;

    /**
     * The password of the user attempting to log in.
     */
    private final String password;
}
