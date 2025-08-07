package com.richardz02.personal_finance.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.richardz02.personal_finance.service.UserService;
import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.dto.UserSignUpLoginRequest;
import com.richardz02.personal_finance.model.User;

@RestController
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ArrayList<User> getUsers() {
        return userService.getAllUsers();
    } 

    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponse> userSignUp(@RequestBody UserSignUpLoginRequest signUpRequest) {
        userService.addUser(signUpRequest.getUsername(), signUpRequest.getPassword()); 
        return ResponseEntity.ok(new ApiResponse("User created"));
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse> userLogin(@RequestBody UserSignUpLoginRequest loginRequest) {
        userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new ApiResponse("Login success"));
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(new ApiResponse("User " + username + " deleted"));
    }
}
