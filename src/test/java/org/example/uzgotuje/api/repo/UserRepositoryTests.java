package org.example.uzgotuje.api.repo;

import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.repository.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        User user = new User("name", "email", "hashedPassword", UserRoles.USER);
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void testFindByEmail() {
        User user = new User("name", "email", "hashedPassword", UserRoles.USER);
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("email").orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindByEmailWithoutMatch(){
        User user = new User("name", "email", "hashedPassword", UserRoles.USER);
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("email2").orElse(null);

        assertThat(foundUser).isNull();
    }
}
