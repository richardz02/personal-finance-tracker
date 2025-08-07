package com.richardz02.personal_finance.exception.transaction;

public class NoTransactionsException extends RuntimeException {
    public NoTransactionsException() {
        super("No transactions available");
    } 
}
