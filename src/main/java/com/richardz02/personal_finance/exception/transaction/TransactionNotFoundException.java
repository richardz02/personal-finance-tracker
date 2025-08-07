package com.richardz02.personal_finance.exception.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String id) {
        super("Transaction with id <" + id + "> not found");
    } 
}
