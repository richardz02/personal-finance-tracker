package com.richardz02.personal_finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.dto.user.UserAuthDTO;
import com.richardz02.personal_finance.model.User;
import com.richardz02.personal_finance.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<User>> userSignup(@RequestBody UserAuthDTO userSignup) {
        User saved = authService.userSignupRequest(userSignup);
        ApiResponse<User> response = new ApiResponse<>("success", "User created", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<Void>> verifyUserLogin(@RequestBody UserAuthDTO userLogin, HttpServletResponse httpResponse) {
        String jwt = authService.authenticateUserLogin(userLogin);
        
        // Set jwt in HTTP authorization header
        httpResponse.setHeader("Authorization", "Bearer " + jwt);

        ApiResponse<Void> response = new ApiResponse<>("success", "Login success", null); 
        return ResponseEntity.ok().body(response);
    }
}
