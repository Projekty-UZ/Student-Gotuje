package org.example.uzgotuje.database.repository.auth;

import org.example.uzgotuje.database.entity.auth.SessionCookie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing SessionCookie entities.
 */
public interface SessionCookieRepository extends JpaRepository<SessionCookie, Long> {

    /**
     * Finds a SessionCookie by its cookie value.
     *
     * @param cookieValue the value of the cookie
     * @return an Optional containing the found SessionCookie, or empty if not found
     */
    Optional<SessionCookie> findByCookieValue(String cookieValue);
}
