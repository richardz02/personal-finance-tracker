package com.richardz02.personal_finance.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richardz02.personal_finance.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Check if a username already exists, return a boolean value
    boolean existsByUsername(String username); 

    // Returns the user if username exists
    Optional<User> findByUsername(String username);
}
