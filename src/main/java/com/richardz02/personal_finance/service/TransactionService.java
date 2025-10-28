package com.richardz02.personal_finance.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.transaction.SummaryWithTransactionsDTO;
import com.richardz02.personal_finance.dto.transaction.TransactionRequestDTO;
import com.richardz02.personal_finance.dto.transaction.TransactionResponseDTO;
import com.richardz02.personal_finance.dto.transaction.TransactionSummaryDTO;
import com.richardz02.personal_finance.exception.transaction.NoTransactionsException;
import com.richardz02.personal_finance.exception.transaction.TransactionNotFoundException;
import com.richardz02.personal_finance.exception.user.UserNotFoundException;
import com.richardz02.personal_finance.model.Transaction;
import com.richardz02.personal_finance.model.TransactionType;
import com.richardz02.personal_finance.repository.TransactionRepository;
import com.richardz02.personal_finance.repository.UserRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // Inject dependency
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();        
    }

    public Transaction addTransaction(TransactionRequestDTO transactionRequest) {
        // Create a new transaction object
        Transaction transaction = new Transaction();

        // Set the fields in the object
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());

        // Save the transaction object to the database
        transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction updateTransaction(UUID id, TransactionRequestDTO transactionRequest) {
        // Get the transaction object from the database by id
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));

        // Reset the fields of this transaction object
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());

        // Save the updated transaction to database
        transactionRepository.save(transaction);

        // Return the updated transaction
        return transaction;
    }

    public TransactionResponseDTO getTransactionById(UUID id) {
        // Get the Transaction object from the database
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));

        // Construct a new instance of TransactionResponseDTO 
        TransactionResponseDTO transactionResponse = new TransactionResponseDTO(
            transaction.getDate(),
            TransactionType.valueOf(transaction.getTransactionType()),
            transaction.getAmount(),
            transaction.getDescription()
        );

        return transactionResponse;
    }

    public void deleteTransaction(UUID id) {
        // If no transactions exist, throw exception
        if (transactionRepository.count() == 0) {
            throw new NoTransactionsException();
        }

        // Check if the transaction can be found in the database
        transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        transactionRepository.deleteById(id);
    }

    public SummaryWithTransactionsDTO getSummary(String period) {
        // Define a start and end date to query the database for transaction summary
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        // Based on the user selected period, set start date
        switch (period.toLowerCase()) {
            case "day":
                startDate = endDate; 
                break;
            case "week": 
                startDate = endDate.with(DayOfWeek.MONDAY);
                break;
            case "month":
                startDate = endDate.withDayOfMonth(1);
                break;
            case "year":
                startDate = endDate.withDayOfYear(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        // Get summary and list of transaction in user selected time period
        TransactionSummaryDTO summary = transactionRepository.transactionSummary(startDate, endDate);
        List<Transaction> transactionsList = transactionRepository.findTransactionsInPeriod(startDate, endDate);

        SummaryWithTransactionsDTO summaryWithTransactions = new SummaryWithTransactionsDTO(summary, transactionsList);
        return summaryWithTransactions;
    }
}
