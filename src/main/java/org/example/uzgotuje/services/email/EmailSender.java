package org.example.uzgotuje.services.email;

public interface EmailSender {
    /**
     * Sends an email to the specified recipient.
     *
     * @param to the recipient's email address
     * @param email the content of the email to be sent
     */
    void send(String to, String email);
}
