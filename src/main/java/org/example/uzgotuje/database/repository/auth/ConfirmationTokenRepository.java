package org.example.uzgotuje.database.repository.auth;

import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing ConfirmationToken entities.
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    /**
     * Finds a ConfirmationToken by its token string.
     *
     * @param token the token string
     * @return an Optional containing the found ConfirmationToken, or empty if not found
     */
    Optional<ConfirmationToken> findByToken(String token);

    /**
     * Deletes all ConfirmationTokens associated with a given User.
     *
     * @param user the User whose tokens are to be deleted
     */
    void deleteByUser(User user);
}
