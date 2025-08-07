package com.richardz02.personal_finance.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.exception.user.InvalidPasswordException;
import com.richardz02.personal_finance.exception.user.UserAlreadyExistsException;
import com.richardz02.personal_finance.exception.user.UserNotFoundException;
import com.richardz02.personal_finance.model.User;
import com.richardz02.personal_finance.utility.PasswordUtil;

@Service
public class UserService {
    // Store users using a hashmap (username(unique) : password hash)
    Map<String, User> users = new HashMap<>();

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();

        for (User user : users.values()) {
            userList.add(user);           
        }

        return userList;
    }

    public void addUser(String username, String password) {
        // If the username already exists, deny new user creation request
        if (users.containsKey(username)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        // Hash the password
        String hashedPassword = PasswordUtil.hashingPassword(password);

        // Create new user
        User newUser = new User(username, hashedPassword);
        users.put(username, newUser);
    }

    public void authenticateUser(String username, String password) {
        // Check if username exists
        if (!users.containsKey(username)) {
            throw new UserNotFoundException("User doesn't exist");
        }
        
        // If username exists, check if password is correct
        User user = users.get(username);
        String hashedPassword = user.getpasswordHash();
        if (!PasswordUtil.verifyPassword(password, hashedPassword)) {
            throw new InvalidPasswordException("Invalid password");
        }
    }

    public void deleteUser(String username) {
        // Check if the username exists
        if (!users.containsKey(username)) {
            throw new UserNotFoundException("User doesn't exist");
        }

        users.remove(username);
    }
}
