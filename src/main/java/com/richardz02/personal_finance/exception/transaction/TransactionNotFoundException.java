package com.richardz02.personal_finance.exception.transaction;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(UUID id) {
        super("Transaction with id <" + id + "> not found");
    } 
}
