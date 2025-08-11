package com.richardz02.personal_finance.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.dto.transaction.TransactionRequestDTO;
import com.richardz02.personal_finance.dto.transaction.TransactionResponseDTO;
import com.richardz02.personal_finance.exception.transaction.NoTransactionsException;
import com.richardz02.personal_finance.exception.transaction.TransactionNotFoundException;
import com.richardz02.personal_finance.model.Transaction;
import com.richardz02.personal_finance.model.TransactionType;
import com.richardz02.personal_finance.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    // Inject dependency
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();        
    }

    public void addTransaction(TransactionRequestDTO transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transactionRepository.save(transaction);
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
}
