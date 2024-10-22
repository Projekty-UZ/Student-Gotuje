package org.example.uzgotuje.database.repository;

import org.example.uzgotuje.database.entity.ConfirmationToken;
import org.example.uzgotuje.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    void deleteByUser(User user);
}
