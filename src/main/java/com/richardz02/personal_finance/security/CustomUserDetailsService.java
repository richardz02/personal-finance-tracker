package com.richardz02.personal_finance.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.exception.user.UserNotFoundException;
import com.richardz02.personal_finance.model.User;
import com.richardz02.personal_finance.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UserNotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
        return new CustomUserDetails(user); 
    }
}
