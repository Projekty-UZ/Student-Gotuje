package org.example.uzgotuje.services.authorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ResetPasswordEmailRequest {
    /**
     * The email of the user requesting a password reset.
     */
    private final String email;

    /**
     * Constructs a new ResetPasswordEmailRequest with the given email.
     *
     * @param email the email of the user requesting a password reset
     */
    @JsonCreator
    public ResetPasswordEmailRequest(@JsonProperty("email") String email) {
        this.email = email;
    }
}