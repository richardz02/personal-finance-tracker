package com.richardz02.personal_finance.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.richardz02.personal_finance.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    
}
