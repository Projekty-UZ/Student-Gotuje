package org.example.uzgotuje.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.services.authorization.*;
import org.example.uzgotuje.services.token.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = authenticationService.register(request);
        if ("Success".equals(response.getMessage()) || "Send new Token".equals(response.getMessage())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<TokenResponse> confirm(@RequestParam("token") String token) {
        TokenResponse response = authenticationService.confirmToken(token);
        if ("Email confirmed".equals(response.getMessage())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Logging User and creating cookie on user side and cookie in database
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
            String cookieValue = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());

            if(Objects.equals(cookieValue, "Invalid credentials")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
            // Set the cookie in the response
            Cookie cookie = new Cookie("SESSION_ID", cookieValue);
            cookie.setHttpOnly(true); // Prevent client-side access to the cookie
            cookie.setPath("/");
            cookie.setMaxAge(2 * 60 * 60); // 2 hours

        System.out.println("Cookie created: " + cookie.getName() + " = " + cookie.getValue());
            response.addCookie(cookie);

            return ResponseEntity.ok("Login successful");
    }

    // Checking if cookie is valid
    @GetMapping("/check")
    public ResponseEntity<String> checkCookie(@CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        if (cookieValue != null && authenticationService.validateCookie(cookieValue)) {
            return ResponseEntity.ok("Cookie is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired cookie");
        }
    }

    // Logging out user and deleting cookie from database
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(value = "SESSION_ID", required = false) String cookieValue, HttpServletResponse response) {
        if (cookieValue != null) {
            authenticationService.logout(cookieValue);

            // Remove the cookie from the client
            Cookie cookie = new Cookie("SESSION_ID", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active session");
        }
    }

    @PostMapping("/resetPasswordEmail")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordEmailRequest email) {
        String response = authenticationService.resetPasswordEmail(email.getEmail());
        if("Passwords do not match".equals(response)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }
        if ("Success".equals(response)) {
            return ResponseEntity.ok("Password reset email sent");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequest passwordRequest) {

        String response = authenticationService.resetPassword(token, passwordRequest.getPassword(), passwordRequest.getRepeatPassword());
        if ("Success".equals(response)) {
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token not found");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUsername(@CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        User user = authenticationService.validateCookieAndGetUser(cookieValue);
        if (cookieValue != null && user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
