package org.example.uzgotuje.database.repository.auth;

import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    void deleteByUser(User user);
}
