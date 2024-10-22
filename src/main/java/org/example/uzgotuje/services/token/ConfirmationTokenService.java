package org.example.uzgotuje.services.token;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.ConfirmationToken;
import org.example.uzgotuje.database.entity.User;
import org.example.uzgotuje.database.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public void deleteConfirmationTokenByUser(User user) {
        confirmationTokenRepository.deleteByUser(user);
    }

}
