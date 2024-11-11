package org.example.uzgotuje.database.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.example.uzgotuje.database.entity.auth.User;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
