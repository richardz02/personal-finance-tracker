package com.richardz02.personal_finance.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.user.UserAuthDTO;
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

    public String authenticateUserLogin(UserAuthDTO userLogin) {
        // Find user by username
        // if user doesn't exist, throw exception
        Optional<User> user = userRepository.findByUsername(userLogin.getUsername());
        if (!user.isPresent()) {
            throw new UserNotFoundException("Username does not exist.");
        }

        String hashedPassword = user.get().getpasswordHash();
        if (PasswordUtil.verifyPassword(userLogin.getPassword(), hashedPassword)) {
            String userId = user.get().getUserId();
            String username = user.get().getUserName();
            return jwtService.generateToken(userId, username);
        }
        else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    public User userSignupRequest(UserAuthDTO userSignup) {
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

        return user;
    }
}
