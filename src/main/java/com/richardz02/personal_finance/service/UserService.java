package com.richardz02.personal_finance.service;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.user.UserAuthDTO;
import com.richardz02.personal_finance.exception.user.UserAlreadyExistsException;
import com.richardz02.personal_finance.model.User;
import com.richardz02.personal_finance.repository.UserRepository;
import com.richardz02.personal_finance.utility.PasswordUtil;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserAuthDTO userSignup) {
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
