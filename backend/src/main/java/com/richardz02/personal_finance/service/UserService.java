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
}
