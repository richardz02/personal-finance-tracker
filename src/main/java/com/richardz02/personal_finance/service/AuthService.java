package com.richardz02.personal_finance.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.user.UserAuthDTO;
import com.richardz02.personal_finance.exception.auth.AuthenticationException;
import com.richardz02.personal_finance.exception.user.UserNotFoundException;
import com.richardz02.personal_finance.repository.UserRepository;
import com.richardz02.personal_finance.utility.PasswordUtil;
import com.richardz02.personal_finance.model.User;;

@Service
public class AuthService {
    private final UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void authenticateUserLogin(UserAuthDTO userLogin) {
        // Find user by username
        // if user doesn't exist, throw exception
        Optional<User> user = userRepository.findByUsername(userLogin.getUsername());
        if (!user.isPresent()) {
            throw new UserNotFoundException("Username does not exist.");
        }

        String hashedPassword = user.get().getpasswordHash();
        if (PasswordUtil.verifyPassword(userLogin.getPassword(), hashedPassword)) {
            // TODO: implement logic when user logs in successful
            System.out.println("Login successful");
        }
        else {
            throw new AuthenticationException("Incorrect password");
        }
    }
}
