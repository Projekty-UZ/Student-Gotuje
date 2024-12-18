package org.example.uzgotuje.services.authorization;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.config.PasswordEncoderConfig;
import org.example.uzgotuje.database.entity.auth.ConfirmationToken;
import org.example.uzgotuje.database.entity.auth.SessionCookie;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.repository.auth.ConfirmationTokenRepository;
import org.example.uzgotuje.database.repository.auth.SessionCookieRepository;
import org.example.uzgotuje.services.UserService;
import org.example.uzgotuje.services.email.EmailValidator;
import org.example.uzgotuje.services.token.ConfirmationTokenService;
import org.example.uzgotuje.services.token.TokenResponse;
import org.example.uzgotuje.services.email.EmailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final SessionCookieRepository sessionCookieRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final EmailSender emailSender;

    public RegistrationResponse register(RegistrationRequest request) {
        boolean isEmailValid=emailValidator.test(request.getEmail());
        if(!isEmailValid){
            return new RegistrationResponse("Email is not valid", "");
        }
        if(!request.getPassword().equals(request.getRepeatPassword())){
            return new RegistrationResponse("passwords do not match", "");
        }
        if(request.getUsername().isEmpty()){
            return new RegistrationResponse("username is empty", "");
        }
        RegistrationResponse response = userService.signUpUser(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRoles.USER
                )
        );

        String link = "http://localhost:8080/auth/confirm?token=" + response.getToken();
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getUsername()
                ,"Confirm your email",
                "Thank you for registering. Please click on the below link to activate your account:" ,
                link
                ));
        return response;
    }

    @Transactional
    public TokenResponse confirmToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (confirmationToken.isEmpty()) {
            return new TokenResponse("Token not found");
        }
        if(confirmationToken.get().getConfirmedAt() != null){
            return new TokenResponse("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.get().getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            return new TokenResponse("Token expired");
        }

        confirmationToken.get().setConfirmedAt(LocalDateTime.now());

        userService.enableUser(confirmationToken.get().getUser().getEmail());

        return new TokenResponse("Email confirmed");
    }

    public String login(String email, String password) {
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (userOpt.isPresent() && passwordEncoderConfig.passwordEncoder().matches(password, userOpt.get().getPassword())) {
            // Create cookie
            String cookieValue = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(2); // Set expiry for 2 hours

            // Store the cookie in DB
            SessionCookie userCookie = new SessionCookie();
            userCookie.setUser(userOpt.get());
            userCookie.setCookieValue(cookieValue);
            userCookie.setExpiryDate(expiryDate);

            sessionCookieRepository.save(userCookie);

            return cookieValue; // return the cookie to the controller to set in the response
        }

        return "Invalid credentials";
    }

    public boolean validateCookie(String cookieValue) {
        Optional<SessionCookie> userCookieOpt = sessionCookieRepository.findByCookieValue(cookieValue);

        if (userCookieOpt.isPresent()) {
            SessionCookie userCookie = userCookieOpt.get();
            // Check if the cookie has expired
            return userCookie.getExpiryDate().isAfter(LocalDateTime.now());
        }

        return false;
    }

    public User validateCookieAndGetUser(String cookieValue) {
        Optional<SessionCookie> userCookieOpt = sessionCookieRepository.findByCookieValue(cookieValue);

        if (userCookieOpt.isPresent()) {
            SessionCookie userCookie = userCookieOpt.get();
            // Check if the cookie hasn't expired
            if (userCookie.getExpiryDate().isAfter(LocalDateTime.now())) {
                return userCookie.getUser();
            }
        }

        return null;
    }

    public void logout(String cookieValue) {
        sessionCookieRepository.findByCookieValue(cookieValue)
                .ifPresent(sessionCookieRepository::delete);
    }

    @Transactional
    public String resetPasswordEmail(String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (userOpt.isPresent()) {
            confirmationTokenService.deleteConfirmationTokenByUser(userOpt.get());
            String newToken = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    newToken,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    userOpt.get()
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:8080/auth/reset?token=" + newToken;
            emailSender.send(email, buildEmail(
                    userOpt.get().getUsername(),
                    "Reset your password",
                    "You have requested to reset your password. Please click on the below link to reset your password:",
                    link
            ));
            return "Success";
        }

        return "User not found";
    }

    public String resetPassword(String token, String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            return "Passwords do not match";
        }
        Optional<ConfirmationToken> confirmationTokenOpt = confirmationTokenRepository.findByToken(token);

        if (confirmationTokenOpt.isPresent()) {
            ConfirmationToken confirmationToken = confirmationTokenOpt.get();
            LocalDateTime expiredAt = confirmationToken.getExpiresAt();

            if (expiredAt.isBefore(LocalDateTime.now())) {
                return "Token expired";
            }

            User user = confirmationToken.getUser();
            String encodedPassword = passwordEncoderConfig.passwordEncoder().encode(password);
            user.setPassword(encodedPassword);

            userService.updateUser(user);

            return "Success";
        }

        return "Token not found";
    }

    private String buildEmail(String name, String use,String message, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" + use + "</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " + message + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
