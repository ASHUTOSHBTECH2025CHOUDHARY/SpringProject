package com.Greeting.greet.Controller;

import com.Greeting.greet.dto.AuthUserDTO;
import com.Greeting.greet.dto.LoginDTO;
import com.Greeting.greet.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthenticationService authenticationService;

    //route for register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AuthUserDTO authUserDTO) {
        return ResponseEntity.ok(authenticationService.registerUser(authUserDTO));
    }
    //post mapping of login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
    }
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email, @Valid @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("password");
        return ResponseEntity.ok(authenticationService.forgotPassword(email, newPassword));
    }
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email, @Valid @RequestBody Map<String, String> requestBody) {
        String currentPassword = requestBody.get("currentPassword");
        String newPassword = requestBody.get("newPassword");
        return ResponseEntity.ok(authenticationService.resetPassword(email, currentPassword, newPassword));
    }
}
