package org.example.uzgotuje.controller;

import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.example.uzgotuje.services.registration.RegistrationRequest;
import org.example.uzgotuje.services.registration.RegistrationResponse;
import org.example.uzgotuje.services.registration.RegistrationService;
import org.example.uzgotuje.services.token.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.register(request);
        if ("Success".equals(response.getMessage())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<TokenResponse> confirm(@RequestParam("token") String token) {
        TokenResponse response = registrationService.confirmToken(token);
        if ("Email confirmed".equals(response.getMessage())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
