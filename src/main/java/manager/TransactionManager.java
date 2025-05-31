package manager;

import model.Transaction;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class TransactionManager {
    // Use the ArrayList data structure to maintain and display the transactions in order
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    // Insert a tranaction
    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    // Remove a transaction
    public void removeTransaction(int id) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getId() == id) {
                transactionList.remove(i);
            }
        }
    }

    public Boolean isEmpty() {
        return transactionList.isEmpty();
    }

    public void printTransactions() {
        System.out.printf("%-5s %-10s %-15s %-10s %-12s\n", "ID", "Type", "Category", "Amount", "Date");
        System.out.println("--------------------------------------------------------------");

        for (Transaction t : transactionList) {
            System.out.printf("%-5d %-10s %-15s $%-9.2f %-12s\n",
                              t.getId(), t.getType(), t.getCategory(), t.getAmount(), t.getDate());
        }
    }
}
