package com.richardz02.personal_finance.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.dto.transaction.SummaryWithTransactionsDTO;
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
    public ResponseEntity<ApiResponse<Transaction>> addTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequest) {
        Transaction saved = transactionService.addTransaction(transactionRequest);
        ApiResponse<Transaction> response = new ApiResponse<Transaction>("success", "Transaction created", saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Update an existing transaction
    @PutMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction(
            @PathVariable UUID id, 
            @RequestBody TransactionRequestDTO transactionUpdateRequest) {
        Transaction updated = transactionService.updateTransaction(id, transactionUpdateRequest);
        ApiResponse<Transaction> response = new ApiResponse<Transaction>("success", "Transaction updated", updated);
        return ResponseEntity.ok().body(response);
    }

    // Return a transaction by id
    @GetMapping("/transactions/{id}")
    public TransactionResponseDTO getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    // Delete transaction by id
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        ApiResponse<Void> response = new ApiResponse<Void>("success", "Transaction deleted", null);
        return ResponseEntity.ok().body(response);
    }

    // Todo: API end points which takes in a param "period" which is specified by the user to view all transactions
    // in this period of time (day, week, month, year, all time)
    @GetMapping("/transactions/summary")
    public ResponseEntity<ApiResponse<SummaryWithTransactionsDTO>> getTransactionSummary(@RequestParam(defaultValue = "day") String period) {
        SummaryWithTransactionsDTO summaryWithTransactions = transactionService.getSummary(period);
        ApiResponse<SummaryWithTransactionsDTO> response = new ApiResponse<SummaryWithTransactionsDTO>("success", "Fetched transaction summary for " + period, summaryWithTransactions);
        return ResponseEntity.ok().body(response);
    }
};
