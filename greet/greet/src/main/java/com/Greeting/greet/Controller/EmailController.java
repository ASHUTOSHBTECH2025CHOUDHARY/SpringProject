package com.Greeting.greet.Controller;

import com.Greeting.greet.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // DTO to accept JSON request
    public static class EmailRequest {
        public String to;
        public String subject;
        public String message;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendEmail(emailRequest.to, emailRequest.subject, emailRequest.message);
            return ResponseEntity.ok("Email sent successfully to " + emailRequest.to);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }
}
