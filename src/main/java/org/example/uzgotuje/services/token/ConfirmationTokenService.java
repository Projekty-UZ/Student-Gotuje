package org.example.uzgotuje.services.token;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.repository.auth.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

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
