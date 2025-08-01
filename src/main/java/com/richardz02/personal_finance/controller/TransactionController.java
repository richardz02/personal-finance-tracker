package com.richardz02.personal_finance.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String addTransaction(@RequestBody Transaction transaction) {
        try {
            transactionService.addTransaction(transaction);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "Transaction added successfully";
    }

    // Delete transaction by id
    @DeleteMapping("/transaction/{id}")
    public String deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
        return "Transaction deleted successfully";
    }
};
