package manager;

import model.Transaction;
import model.Category;
import model.TransactionType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.lang.reflect.Type;


public class TransactionManager {
    // Adapter class to convert LocalDate object to json
    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString());
        }
    }

    // Use the ArrayList data structure to maintain and display the transactions in order
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    // Use the hashmap data structure to categorize transactions and for fast access time
    private HashMap<Category, ArrayList<Transaction>> transactionMap = new HashMap<>();

    // Insert a tranaction
    public void addTransaction(Transaction transaction) {
        // Add transaction to ArrayList
        transactionList.add(transaction);

        // Add transaction to HashMap
        if (!transactionMap.containsKey(transaction.getCategory())) {
            transactionMap.put(transaction.getCategory(), new ArrayList<>());
        }
        transactionMap.get(transaction.getCategory()).add(transaction);
    }

    // Remove a transaction
    public void removeTransaction(int id) {
        Transaction toRemove = null;

        // Remove the transaction from ArrayList
        Iterator<Transaction> itr = transactionList.iterator();
        while (itr.hasNext()) {
            Transaction t = itr.next();
            if (t.getId() == id) {
                toRemove = t;
                itr.remove();

                break;
            }
        }

        if (toRemove == null) {
            System.out.println("Transaction with ID " + id + " not found");
            return;
        }

        ArrayList<Transaction> categoryTransactionList = transactionMap.get(toRemove.getCategory());
        if (categoryTransactionList != null) {
            categoryTransactionList.removeIf(t -> t.getId() == id);

            if (categoryTransactionList.isEmpty()) {
                transactionMap.remove(toRemove.getCategory());
            }
        }
    }

    // Check if there exists any transactions
    public Boolean isEmpty() {
        return transactionList.isEmpty();
    }

    // Print all transactions
    public void viewAllTransactions() {
        if (transactionList.isEmpty()) {
            System.out.println("No transactions");
            return;
        }

        System.out.printf("%-5s %-10s %-15s %-10s %-12s\n", "ID", "Type", "Category", "Amount", "Date");
        System.out.println("--------------------------------------------------------------");

        for (Transaction t : transactionList) {
            System.out.printf("%-5d %-10s %-15s $%-9.2f %-12s\n",
                              t.getId(), t.getType(), t.getCategory(), t.getAmount(), t.getDate());
        }
    }

    // Print transactions in a specific category
    public void viewSpecificCategoryTransactions(Category category) {
        // Check if the HashMap contains the category
        if (!transactionMap.containsKey(category)) {
            System.out.println("No transactions found in this category");
            return;
        }

        // Get the corresponding list of transactions for the specified category
        ArrayList<Transaction> categoryTransactionList = transactionMap.get(category);

        // Print out transactions
        System.out.println("==================================");
        System.out.println("Transactions in category: " + category);
        System.out.println("==================================");
        for (Transaction t : categoryTransactionList) {
            System.out.printf("Date       : %s\n", t.getDate());
            System.out.printf("Type       : %s\n", t.getType());
            System.out.printf("Amount     : %.2f\n", t.getAmount());
            System.out.printf("Description: %s\n", t.getDescription());
            System.out.println("---------------------------------");
        }
    }

    // Print all transactions, grouped by category
    public void viewAllCategoryTransactions() {
        // If there are no transactions, print message and return
        if (transactionList.isEmpty()) {
            System.out.println("No transactions");
            return;
        }

        for (Category category : transactionMap.keySet()) {
            // Get the transaction list for each category
            ArrayList<Transaction> categoryTransactionList = transactionMap.get(category);

            System.out.println("=================================");
            System.out.println("Category: " + category);
            System.out.println("=================================");

            // For each transaction list, print out all transactions
            for (Transaction t : categoryTransactionList) {
                System.out.printf("Date       : %s\n", t.getDate());
                System.out.printf("Type       : %s\n", t.getType());
                System.out.printf("Amount     : %.2f\n", t.getAmount());
                System.out.printf("Description: %s\n", t.getDescription());
                System.out.println("---------------------------------");
            }
        }
    }

    // Outputs total income, total expenses, and net balance
    public void viewSummary() {
        double totalIncome = 0;
        double totalExpense = 0;
        double netBalance = 0;

        for (Transaction t : transactionList) {
            if (t.getType() == TransactionType.INCOME) {
                totalIncome += t.getAmount();
            } else if (t.getType() == TransactionType.EXPENSE) {
                totalExpense += t.getAmount();
            }
        }

        netBalance = totalIncome - totalExpense;

        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Net Balance: " + netBalance);
    }

    public void loadFromFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) return; // If file doesn't exist, return

            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
            Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType();
            FileReader reader = new FileReader(file);

            transactionList = gson.fromJson(reader, listType);
            reader.close();

            if (transactionList != null) {
                for (Transaction t : transactionList) {
                    Category category = t.getCategory();
                    if (!transactionMap.containsKey(category)) {
                        transactionMap.put(category, new ArrayList<>());
                    }

                    transactionMap.get(category).add(t);
                }
            }

            // Fall back, in case the file has no data
            if (transactionList == null) {
                transactionList = new ArrayList<Transaction>();
            }
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveToFile(String fileName) {
		try {
			Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
			FileWriter writer = new FileWriter(fileName);
			gson.toJson(transactionList, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}

    }
}
