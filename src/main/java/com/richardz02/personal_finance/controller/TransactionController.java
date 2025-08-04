package com.richardz02.personal_finance.controller;

import java.io.IOException;
import java.util.*;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.model.Transaction;
import com.richardz02.personal_finance.service.TransactionService;


@RestController
public class TransactionController {

    private TransactionService transactionService; 

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Get all transactions
    @GetMapping("/transactions")
    public ArrayList<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    // Add new transaction
    @PostMapping("/add-transaction")
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        try {
            transactionService.addTransaction(transaction);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return transaction;
    }

    // Delete transaction by id
    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<ApiResponse> deleteTransaction(@PathVariable String id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.ok(new ApiResponse("Transaction deleted"));
        } catch (IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        }
    
    }

    // Save all transactions in a json file
    @PostMapping("/transactions/save")
    public ResponseEntity<ApiResponse> saveTransactions() {
        try {
            transactionService.saveToFile();
            return ResponseEntity.ok(new ApiResponse("Saved to file"));
        
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
        }
    }

    // Load all transactions from file
    @GetMapping("/transactions/load")
    public ResponseEntity<ApiResponse> loadTransactions() {
        try {
            transactionService.loadFromFile();
            return ResponseEntity.ok(new ApiResponse("Loaded from file"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to load from file"));
        }
    }
};
