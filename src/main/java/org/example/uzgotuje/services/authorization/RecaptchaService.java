package org.example.uzgotuje.services.authorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    @Value("${google.recaptcha.secret-key}")
    private String recaptchaSecretKey;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyRecaptcha(String recaptchaResponse) {
        // Prepare request body
        String url = RECAPTCHA_VERIFY_URL + "?secret=" + recaptchaSecretKey + "&response=" + recaptchaResponse;

        // Send POST request to Google's API
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, null, String.class);

        // Parse the response (it's a JSON object with a success field)
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();
            System.out.println(responseBody);
            // Assuming you use a JSON library to parse the response, such as Jackson
            return responseBody.contains("\"success\": true");
        }

        return false;
    }
}
