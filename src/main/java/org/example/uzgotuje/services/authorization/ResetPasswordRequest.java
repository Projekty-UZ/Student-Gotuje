package org.example.uzgotuje.services.authorization;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ResetPasswordRequest {
    /**
     * The new password for the user.
     */
    private final String password;

    /**
     * The repeated password for confirmation.
     */
    private final String repeatPassword;
}