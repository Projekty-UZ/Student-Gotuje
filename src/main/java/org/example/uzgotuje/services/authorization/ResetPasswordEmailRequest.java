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
    private final String email;
    @JsonCreator
    public ResetPasswordEmailRequest(@JsonProperty("email") String email) {
        this.email = email;
    }

}