package org.example.uzgotuje.database.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.example.uzgotuje.database.entity.auth.User;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User by their email address.
     *
     * @param email the email address of the User
     * @return an Optional containing the found User, or empty if not found
     */
    Optional<User> findByEmail(String email);
}
