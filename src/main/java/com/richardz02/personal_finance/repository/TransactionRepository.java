package com.richardz02.personal_finance.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richardz02.personal_finance.model.Transaction;
import com.richardz02.personal_finance.dto.transaction.TransactionSummary;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    /*  Since we are using enum class to represent the transaction types, in the database
        income will have a value of 0, and expense will have a value of 1
    */
    @Query("""
            SELECT new com.richardz02.personal_finance.dto.transaction.TransactionSummary (
                SUM(CASE WHEN t.transactionType = 0 THEN t.amount ELSE 0 END),
                SUM(CASE WHEN t.transactionType = 1 THEN t.amount ELSE 0 END),
                SUM(CASE WHEN t.transactionType = 0 THEN t.amount ELSE 0 END) -
                SUM(CASE WHEN t.transactionType = 1 THEN t.amount ELSE 0 END)
            )
            FROM Transaction t
            """)
    TransactionSummary transactionSummary();
}
