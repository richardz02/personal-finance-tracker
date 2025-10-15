package com.richardz02.personal_finance.dto.transaction;

import java.util.List;

import com.richardz02.personal_finance.model.Transaction;

public class SummaryWithTransactions {
    TransactionSummary summary;
    List<Transaction> transactions;
    
    public SummaryWithTransactions(TransactionSummary summary, List<Transaction> transactions) {
        this.summary = summary;
        this.transactions = transactions;
    }
    
    public TransactionSummary getSummary() {
        return summary;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
