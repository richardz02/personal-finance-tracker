package com.richardz02.personal_finance.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.richardz02.personal_finance.dto.ApiResponse;
import com.richardz02.personal_finance.exception.transaction.NoTransactionsException;
import com.richardz02.personal_finance.exception.transaction.TransactionNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidFormat(HttpMessageNotReadableException e) {
        String message = "Malformed JSON request";

        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = "";

            // Extract the problematic field name from the path
            if (!ife.getPath().isEmpty()) {
                fieldName = ife.getPath().get(0).getFieldName();
            }

            Class<?> targetType = ife.getTargetType();

            if (targetType.isEnum()) {
                message = "Invalid value for field '" + fieldName + "'. Allowed values: " +
                          Arrays.toString(targetType.getEnumConstants());
            } else if (targetType == Double.class || targetType == double.class) {
                message = "Field '" + fieldName + "' must be a valid number";
            } else {
                message = "Invalid value for field '" + fieldName + "'";
            }
        }

        ApiResponse<Void> response = new ApiResponse<Void>("failure", message, null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoTransactionsException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoTransactionError(NoTransactionsException e) {
        ApiResponse<Void> response = new ApiResponse<Void>("failure", e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTransactionNotFound(TransactionNotFoundException e) {
        ApiResponse<Void> response = new ApiResponse<Void>("failure", e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}