package com.richardz02.personal_finance.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.dto.transaction.TransactionRequestDTO;
import com.richardz02.personal_finance.dto.transaction.TransactionResponseDTO;
import com.richardz02.personal_finance.model.Transaction;
import com.richardz02.personal_finance.service.TransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private final TransactionService transactionService; 

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Get all transactions
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Add new transaction
    @PostMapping("/transactions")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequest) {
        transactionService.addTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Created successfully"));
    }

    // Return a transaction by id
    @GetMapping("/transactions/{id}")
    public TransactionResponseDTO getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    // Delete transaction by id
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(new ApiResponse("Transaction deleted"));
    }
};
