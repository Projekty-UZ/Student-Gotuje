package org.example.uzgotuje.services.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationResponse {
    private final String message;
    private final String token;
}
