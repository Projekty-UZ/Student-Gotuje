package org.example.uzgotuje.api.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.uzgotuje.database.entity.auth.SessionCookie;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.repository.auth.SessionCookieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessionCookieRepositoryTests {
    @Autowired
    private SessionCookieRepository sessionCookieRepository;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void userData() {
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        User testUser = new User("name","email","hashedPassword", UserRoles.USER);
        entityManager.persist(testUser);
        entityManager.flush();
    }
    @Test
    public void testSave() {
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        SessionCookie sessionCookie = new SessionCookie();
        sessionCookie.setCookieValue("cookieValue");
        sessionCookie.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        sessionCookie.setUser(testUser);
        SessionCookie savedCookie = sessionCookieRepository.save(sessionCookie);

        assertThat(savedCookie).isNotNull();
        assertThat(savedCookie.getId()).isNotNull();
        assertThat(savedCookie.getCookieValue()).isEqualTo(sessionCookie.getCookieValue());
    }

    @Test
    public void testFindByCookieValue() {
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        SessionCookie sessionCookie = new SessionCookie();
        sessionCookie.setCookieValue("cookieValue");
        sessionCookie.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        sessionCookie.setUser(testUser);
        sessionCookieRepository.save(sessionCookie);

        SessionCookie foundCookie = sessionCookieRepository.findByCookieValue("cookieValue").orElse(null);

        assertThat(foundCookie).isNotNull();
        assertThat(foundCookie.getCookieValue()).isEqualTo(sessionCookie.getCookieValue());
    }

    @Test
    public void testFindByCookieValueWithoutMatch(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        SessionCookie sessionCookie = new SessionCookie();
        sessionCookie.setCookieValue("cookieValue");
        sessionCookie.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        sessionCookie.setUser(testUser);
        sessionCookieRepository.save(sessionCookie);

        SessionCookie foundCookie = sessionCookieRepository.findByCookieValue("anotherCookieValue").orElse(null);

        assertThat(foundCookie).isNull();
    }
}
