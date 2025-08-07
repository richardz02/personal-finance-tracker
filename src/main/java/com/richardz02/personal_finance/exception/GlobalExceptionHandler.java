package com.richardz02.personal_finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.exception.transaction.NoTransactionsException;
import com.richardz02.personal_finance.exception.transaction.TransactionNotFoundException;
import com.richardz02.personal_finance.exception.user.InvalidPasswordException;
import com.richardz02.personal_finance.exception.user.UserAlreadyExistsException;
import com.richardz02.personal_finance.exception.user.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoTransactionsException.class)
    public ResponseEntity<ApiResponse> handleNoTransactions(NoTransactionsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiResponse> handleTransactionNotFound(TransactionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExists(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse> handlerInvalidPassword(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse(e.getMessage()));
    }
}
