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
import com.richardz02.personal_finance.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponse<User>> userSignup(@RequestBody UserAuthDTO userSignup) {
        User saved = userService.createUser(userSignup);
        ApiResponse<User> response = new ApiResponse<>("success", "User created", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse<Void>> userLogin(@RequestBody UserAuthDTO userLogin) {
        authService.authenticateUserLogin(userLogin); 
        ApiResponse<Void> response = new ApiResponse<Void>("success", "Login success", null);
        return ResponseEntity.ok().body(response);
    }
}
