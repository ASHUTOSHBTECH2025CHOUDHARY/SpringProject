package com.Greeting.greet.service;

import com.Greeting.greet.dto.AuthUserDTO;
import com.Greeting.greet.dto.LoginDTO;
import com.Greeting.greet.model.AuthUser;
import com.Greeting.greet.repository.AuthUserRepository;
import com.Greeting.greet.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    //Registeruser sending mail after successful registration
    public String registerUser(AuthUserDTO authUserDTO) {
        Optional<AuthUser> existingUser = authUserRepository.findByEmail(authUserDTO.getEmail());
        if (existingUser.isPresent()) {
            return "Email is already registered!";
        }

        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName());
        user.setEmail(authUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));

        authUserRepository.save(user);
        String token = jwtUtil.generateToken(authUserDTO.getEmail());

        try {
            sendRegistrationEmailWithToken(authUserDTO.getEmail(), authUserDTO.getFirstName(), token);
            return "User registered successfully! Check your email for your authentication token.";
        } catch (Exception e) {
            // Log the error but don't fail registration if email fails
            System.err.println("Failed to send registration email: " + e.getMessage());
            return "User registered successfully! Token generated but email sending failed.";
        }
    }
//login and sending token after successful login
    public String loginUser(LoginDTO loginDTO) {
        Optional<AuthUser> user = authUserRepository.findByEmail(loginDTO.getEmail());

        if (user.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            return jwtUtil.generateToken(loginDTO.getEmail());
        }
        return "Invalid email or password!";
    }

    private void sendRegistrationEmailWithToken(String toEmail, String username, String token) {
        String subject = "Registration Successful - Your Authentication Token";
        String message = "Dear " + username + " "+token ;

        emailService.sendEmail(toEmail, subject, message);
    }
    public String forgotPassword(String email, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Sorry! We cannot find the user email: " + email;
        }

        AuthUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);

        sendPasswordResetEmail(email);
        return "Password has been changed successfully!";
    }

    private void sendPasswordResetEmail(String toEmail) {
        String subject = "Password Reset Confirmation";
        String message = "Your password has been successfully reset.";
        emailService.sendEmail(toEmail, subject, message);
    }
    public String resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "User not found with email: " + email;
        }

        AuthUser user = userOptional.get();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "Current password is incorrect!";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);
        sendPasswordResetEmail(email);
        return "Password reset successfully!";
    }

}