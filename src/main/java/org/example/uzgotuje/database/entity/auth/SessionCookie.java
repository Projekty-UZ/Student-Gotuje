package org.example.uzgotuje.database.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a session cookie used for user authentication.
 */
@Entity
@Getter
@Setter
public class SessionCookie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String cookieValue;
    private LocalDateTime expiryDate;
}
