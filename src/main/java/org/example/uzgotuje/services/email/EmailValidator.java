package org.example.uzgotuje.services.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );
    @Override
    public boolean test(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
