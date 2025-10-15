package com.richardz02.personal_finance.repository;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.richardz02.personal_finance.model.Transaction;
import com.richardz02.personal_finance.dto.transaction.TransactionSummary;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    /** This query returns the total income, total expense, and balance in a given time period
    *
    *   Since we are using enum class to represent the transaction types, in the database
    *   income will have a value of 0, and expense will have a value of 1
    */
    @Query("""
            SELECT new com.richardz02.personal_finance.dto.transaction.TransactionSummary (
                SUM(CASE WHEN t.transactionType = 0 THEN t.amount ELSE 0 END),
                SUM(CASE WHEN t.transactionType = 1 THEN t.amount ELSE 0 END),
                SUM(CASE WHEN t.transactionType = 0 THEN t.amount ELSE 0 END) -
                SUM(CASE WHEN t.transactionType = 1 THEN t.amount ELSE 0 END)
            )
            FROM Transaction t
            WHERE t.date BETWEEN :startDate AND :endDate
            """)
    TransactionSummary transactionSummary(LocalDate startDate, LocalDate endDate);

    // This query returns a list of transactions in a given time period
    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.date BETWEEN :startDate AND :endDate
            """)
    List<Transaction> findTransactionsInPeriod(LocalDate startDate, LocalDate endDate);
}
