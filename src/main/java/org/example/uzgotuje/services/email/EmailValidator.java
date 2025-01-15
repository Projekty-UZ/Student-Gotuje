package org.example.uzgotuje.services.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Service for validating email addresses.
 */
@Service
public class EmailValidator implements Predicate<String> {
    /**
     * The pattern used to validate email addresses.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    /**
     * Tests whether the given email matches the email pattern.
     *
     * @param email the email address to be validated
     * @return true if the email matches the pattern, false otherwise
     */
    @Override
    public boolean test(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
