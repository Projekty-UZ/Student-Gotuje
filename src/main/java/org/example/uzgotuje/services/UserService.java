package org.example.uzgotuje.services;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.config.PasswordEncoderConfig;
import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.repository.auth.UserRepository;
import org.example.uzgotuje.services.authorization.RegistrationResponse;
import org.example.uzgotuje.services.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing users.
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    /**
     * Repository for managing users.
     */
    private final UserRepository userRepository;

    /**
     * Configuration for password encoding.
     */
    private final PasswordEncoderConfig passwordEncoderConfig;

    /**
     * Service for managing confirmation tokens.
     */
    private final ConfirmationTokenService confirmationTokenService;

    /**
     * Loads a user by their email.
     *
     * @param email the email of the user
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return an optional containing the user if found, or empty if not found
     */
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return the registration response containing a message and token
     */
    public RegistrationResponse signUpUser(User user){
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        // If user exists, delete old token and send new one
        if(userExists){
            confirmationTokenService.deleteConfirmationTokenByUser(user);
            String newToken = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    newToken,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            return new RegistrationResponse("Send new Token", newToken);
        }

        // If user doesn't exist, encode password and save user
        String encodedPassword = passwordEncoderConfig.passwordEncoder().encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),  user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return new RegistrationResponse("Success",token);
    }

    /**
     * Enables a user by their email.
     *
     * @param email the email of the user
     */
    public void enableUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        user.setEnabled(true);
        userRepository.save(user);
    }

    /**
     * Updates a user.
     *
     * @param user the user to update
     */
    public void updateUser(User user){
        userRepository.save(user);
    }
}