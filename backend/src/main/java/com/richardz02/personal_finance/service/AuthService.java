package com.richardz02.personal_finance.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.dto.user.UserAuthRequestDTO;
import com.richardz02.personal_finance.dto.user.UserAuthResponseDTO;
import com.richardz02.personal_finance.exception.user.UserAlreadyExistsException;
import com.richardz02.personal_finance.repository.UserRepository;
import com.richardz02.personal_finance.security.CustomUserDetails;
import com.richardz02.personal_finance.utility.PasswordUtil;
import com.richardz02.personal_finance.model.User;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserAuthResponseDTO userSignupRequest(UserAuthRequestDTO userSignup) {
        if (userRepository.existsByUsername(userSignup.getUsername())) {
            throw new UserAlreadyExistsException("Username is not available");
        }

        // Create a new user object
        User user = new User();

        // Set username and password hash
        user.setUserName(userSignup.getUsername());
        user.setpasswordHash(passwordEncoder.encode(userSignup.getPassword()));
        
        // Save created user in database
        userRepository.save(user);

        UserAuthResponseDTO userSignupResponse = new UserAuthResponseDTO(user.getUserId(), user.getUserName());
        
        return userSignupResponse;
    }

    public ResponseEntity<ApiResponse<UserAuthResponseDTO>> authenticateUserLogin(UserAuthRequestDTO userLogin) {
        // Authenticate user login request
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLogin.getUsername(),
                userLogin.getPassword()
        ));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userid = userDetails.getUserid();
        String username = userDetails.getUsername();
        String token = jwtService.generateToken(userid, username);

        UserAuthResponseDTO userLoginResponse = new UserAuthResponseDTO(userid, username);

        ApiResponse<UserAuthResponseDTO> response = new ApiResponse<>("success", "Login success", userLoginResponse);

        return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(response);

    }
}
