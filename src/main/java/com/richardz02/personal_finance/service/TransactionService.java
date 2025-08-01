package com.richardz02.personal_finance.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.richardz02.personal_finance.model.Transaction;

@Service
public class TransactionService {
    ArrayList<Transaction> transactions = new ArrayList<>();
    
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void deleteTransaction(String id) {
        // First check if the transaction array is empty
        if (transactions.isEmpty()) {
            System.err.println("No transactions");
            return;
        }

        // Otherwise, look for the transaction to delete
        Iterator<Transaction> it = transactions.iterator();
        while (it.hasNext()) {
            Transaction t = it.next();
            if (t.getId().equals(id)) {
                it.remove();
                System.out.println("Transaction removed");
                return;
            }
        }

        System.err.println("Transaction with id: " + id + " not found");
    }

    public ArrayList<Transaction> getAllTransactions() {
        return transactions;        
    }
}
