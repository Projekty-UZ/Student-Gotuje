package org.example.uzgotuje.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for sending emails.
 */
@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    /**
     * The JavaMailSender used to send emails.
     */
    private final JavaMailSender mailSender;

    /**
     * The logger for logging email sending errors.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    /**
     * Sends an email to the specified recipient asynchronously.
     *
     * @param to the recipient's email address
     * @param email the content of the email to be sent
     */
    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("gamespabloplay@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
