package org.example.uzgotuje.api.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.repository.auth.ConfirmationTokenRepository;
import org.example.uzgotuje.database.repository.auth.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConfirmationTokenRepositoryTests {
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void userData(){
        entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
        User testUser = new User("name","email","hashedPassword", UserRoles.USER);
        entityManager.persist(testUser);
        entityManager.flush();
    }
    @Test
    public void testSave(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken("tokenString", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), testUser);

        // when
        ConfirmationToken savedConfirmationToken = confirmationTokenRepository.save(confirmationToken);

        // then
        assertThat(savedConfirmationToken).isNotNull();
        assertThat(savedConfirmationToken.getId()).isNotNull();
        assertThat(savedConfirmationToken.getToken()).isEqualTo(confirmationToken.getToken());
    }

    @Test
    public void testFindByToken(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken("tokenString", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), testUser);
        confirmationTokenRepository.save(confirmationToken);

        // when
        ConfirmationToken foundConfirmationToken = confirmationTokenRepository.findByToken("tokenString").orElse(null);

        // then
        assertThat(foundConfirmationToken).isNotNull();
        assertThat(foundConfirmationToken.getToken()).isEqualTo(confirmationToken.getToken());
    }

    @Test
    public void testFindByTokenWithoutMatch(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        // given
        ConfirmationToken confirmationToken = new ConfirmationToken("tokenString", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), testUser);
        confirmationTokenRepository.save(confirmationToken);

        // when
        ConfirmationToken foundConfirmationToken = confirmationTokenRepository.findByToken("anotherTokenString").orElse(null);

        // then
        assertThat(foundConfirmationToken).isNull();
    }

    @Test
    public void testDeleteByUser(){
        User testUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = 'email'", User.class).getSingleResult();
        // given

        ConfirmationToken confirmationToken = new ConfirmationToken("tokenString", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), testUser);
        confirmationTokenRepository.save(confirmationToken);

        // when
        confirmationTokenRepository.deleteByUser(testUser);

        // then
        assertThat(confirmationTokenRepository.findByToken("tokenString").orElse(null)).isNull();
    }
}
