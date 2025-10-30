package com.richardz02.personal_finance.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.user.UserAuthRequestDTO;
import com.richardz02.personal_finance.dto.user.UserAuthResponseDTO;
import com.richardz02.personal_finance.exception.auth.AuthenticationException;
import com.richardz02.personal_finance.exception.user.UserAlreadyExistsException;
import com.richardz02.personal_finance.exception.user.UserNotFoundException;
import com.richardz02.personal_finance.repository.UserRepository;
import com.richardz02.personal_finance.utility.PasswordUtil;
import com.richardz02.personal_finance.model.User;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<UserAuthResponseDTO> authenticateUserLogin(UserAuthRequestDTO userLogin) {
        // Find user by username
        // if user doesn't exist, throw exception
        User user = userRepository.findByUsername(userLogin.getUsername()).orElseThrow(() -> new UserNotFoundException("Username not found."));

        // If user exists, compare hashed passwords
        // If passwords match, user has successfully identified themselves
        // Send response back along with jwt
        if (PasswordUtil.verifyPassword(userLogin.getPassword(), user.getpasswordHash())) {
            String userId = user.getUserId();
            String username = user.getUserName();
            String token =  jwtService.generateToken(userId, username);

            UserAuthResponseDTO userAuthResponse = new UserAuthResponseDTO(userId, username);
            
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(userAuthResponse);
        }
        else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    public UserAuthResponseDTO userSignupRequest(UserAuthRequestDTO userSignup) {
        if (userRepository.existsByUsername(userSignup.getUsername())) {
            throw new UserAlreadyExistsException("Username is not available");
        }

        // Create a new user object
        User user = new User();

        // Set username and password hash
        user.setUserName(userSignup.getUsername());
        user.setpasswordHash(PasswordUtil.hashingPassword(userSignup.getPassword()));
        
        // Save created user in database
        userRepository.save(user);

        UserAuthResponseDTO userSignupResponse = new UserAuthResponseDTO(user.getUserId(), user.getUserName());

        return userSignupResponse;
    }
}
