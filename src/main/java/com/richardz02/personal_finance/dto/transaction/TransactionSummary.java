package com.richardz02.personal_finance.dto.transaction;

// Record class for transaction summary, containing data transfer object
public record TransactionSummary(Double totalIncome, Double totalExpense, Double balance) {}