package org.example.uzgotuje.services;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.config.PasswordEncoderConfig;
import org.example.uzgotuje.database.entity.ConfirmationToken;
import org.example.uzgotuje.database.entity.User;
import org.example.uzgotuje.database.repository.UserRepository;
import org.example.uzgotuje.services.registration.RegistrationResponse;
import org.example.uzgotuje.services.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    public RegistrationResponse signUpUser(User user){
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if(userExists){
            return new RegistrationResponse("Email already taken","");
        }

        String encodedPassword = passwordEncoderConfig.passwordEncoder().encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),  user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return new RegistrationResponse("Success",token);
    }

    public void enableUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        user.setEnabled(true);
        userRepository.save(user);
    }
}
