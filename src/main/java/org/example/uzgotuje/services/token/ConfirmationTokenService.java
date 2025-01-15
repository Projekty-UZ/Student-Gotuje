package org.example.uzgotuje.services.token;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.repository.auth.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing confirmation tokens.
 */
@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    /**
     * Repository for managing confirmation tokens.
     */
    private final ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * Saves a confirmation token.
     *
     * @param token the confirmation token to save
     */
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    /**
     * Deletes a confirmation token by user.
     *
     * @param user the user whose confirmation token is to be deleted
     */
    public void deleteConfirmationTokenByUser(User user) {
        confirmationTokenRepository.deleteByUser(user);
    }
}
