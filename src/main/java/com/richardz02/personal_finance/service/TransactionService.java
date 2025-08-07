package com.richardz02.personal_finance.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
            throw new IllegalStateException("No transactions");
        }

        // Otherwise, look for the transaction to delete
        Iterator<Transaction> it = transactions.iterator();
        while (it.hasNext()) {
            Transaction t = it.next();
            if (t.getId().equals(id)) {
                it.remove();
                return;
            }
        }

        throw new NoSuchElementException("Transaction with id " + id + " doesn't exist");
    }

    public ArrayList<Transaction> getAllTransactions() {
        return transactions;        
    }

    public void saveToFile() throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        // Register the time module because Jackson doesn't support handling the Java LocalDate object by default
        mapper.registerModule(new JavaTimeModule());

        // Enable pretty printing for better readability (Don't use in production, only for local development)
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Serialize the array of transactions into json format string
        String jsonString = mapper.writeValueAsString(transactions);
                    
        // Save to file 
        FileWriter writer = new FileWriter("transactions.json");
        writer.write(jsonString); 
        writer.close();
    }

    public void loadFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Read entire json file content into a string
        String fileContent = Files.readString(Paths.get("transactions.json"));
        transactions = mapper.readValue(fileContent, new TypeReference<ArrayList<Transaction>>() {});
    }
}
