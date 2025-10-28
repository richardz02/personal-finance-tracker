package com.richardz02.personal_finance.dto.transaction;

import java.util.List;

import com.richardz02.personal_finance.model.Transaction;

public class SummaryWithTransactionsDTO {
    TransactionSummaryDTO summary;
    List<Transaction> transactions;
    
    public SummaryWithTransactionsDTO(TransactionSummaryDTO summary, List<Transaction> transactions) {
        this.summary = summary;
        this.transactions = transactions;
    }
    
    public TransactionSummaryDTO getSummary() {
        return summary;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
