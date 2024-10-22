package org.example.uzgotuje.database.repository;

import org.example.uzgotuje.database.entity.SessionCookie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionCookieRepository extends JpaRepository<SessionCookie, Long> {
    Optional<SessionCookie> findByCookieValue(String cookieValue);
}
