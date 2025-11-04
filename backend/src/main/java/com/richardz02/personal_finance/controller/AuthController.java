package com.richardz02.personal_finance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.dto.user.UserAuthRequestDTO;
import com.richardz02.personal_finance.dto.user.UserAuthResponseDTO;
import com.richardz02.personal_finance.service.AuthService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<UserAuthResponseDTO>> userSignup(@RequestBody UserAuthRequestDTO userSignup) {
        UserAuthResponseDTO userInfo = authService.userSignupRequest(userSignup);
        ApiResponse<UserAuthResponseDTO> response = new ApiResponse<>("success", "User created", userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserAuthResponseDTO> verifyUserLogin(@RequestBody UserAuthRequestDTO userLogin) {
        return authService.authenticateUserLogin(userLogin);
    }
}
