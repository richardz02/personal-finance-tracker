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

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }
}
