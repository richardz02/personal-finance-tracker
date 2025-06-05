package app;

import manager.TransactionManager;
import model.Category;
import model.Transaction;
import model.TransactionType;

import java.util.Scanner;

public class App {

    public void run() {
        // Create a transaction manager
        TransactionManager manager = new TransactionManager();
        manager.loadFromFile("personal_finance.json");

        while (true) {
            // Print out menu
            System.out.println("===== PERSONAL FINANCE TRACKER =====");
            System.out.println("1. Add income");
            System.out.println("2. Add expense");
            System.out.println("3. View all transactions");
            System.out.println("4. View by category");
            System.out.println("5. View summary");
            System.out.println("6. Save and exit");
            System.out.println("====================================");
            System.out.print("Select an option: ");

            Scanner sc = new Scanner(System.in);
            // Set default value to -1
            // User input validation
            int action = -1;
            try {
                action = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }

            switch(action) {
                case 1:
                    {
                        double amount;
                        while (true) {
                            System.out.print("Enter amount ($): ");
                            try {
                                amount = Double.parseDouble(sc.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid number.");
                            }
                        }

                        System.out.print("Enter description: ");
                        String description = sc.nextLine();

                        Category category;
                        while (true) {
                            System.out.print("Select category ( ");
                            for (Category tag : Category.values()) {
                                System.out.print(tag + " ");
                            }
                            System.out.print("): ");
                            String strCategory = sc.nextLine().toUpperCase();

                            try {
                                category = Category.valueOf(strCategory);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Category does not exist");
                            }
                        }

                        Transaction newTransaction = new Transaction(amount, TransactionType.INCOME, category, description);
                        manager.addTransaction(newTransaction);

                        System.out.println("Added transaction");
                    }

                    break;
                case 2:
                    {
                        double amount;
                        while (true) {
                            System.out.print("Enter amount ($): ");
                            try {
                                amount = Double.parseDouble(sc.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid number.");
                            }
                        }

                        System.out.print("Enter description: ");
                        String description = sc.nextLine();

                        Category category;
                        while (true) {
                            System.out.print("Select category ( ");
                            for (Category tag : Category.values()) {
                                System.out.print(tag + " ");
                            }
                            System.out.print("): ");
                            String strCategory = sc.nextLine().toUpperCase();

                            try {
                                category = Category.valueOf(strCategory);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Category does not exist");
                            }
                        }

                        Transaction newTransaction = new Transaction(amount, TransactionType.EXPENSE, category, description);
                        manager.addTransaction(newTransaction);

                        System.out.println("Added transaction");
                    }

                    break;
                case 3:
                    manager.viewAllTransactions();

                    break;
                case 4:
                    int choice = -1;

                    while (true) {
                        // Display menu choices for view by category
                        System.out.println("View by category: ");
                        System.out.println("1. View one category");
                        System.out.println("2. View grouped all by categories");
                        System.out.print("Select an option: ");

                        try {
                            choice = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }

                    }

                    switch (choice) {
                        case 1:
                            Category category;

                            while (true) {
                                System.out.println("Available categories: ");
                                for (Category tag : Category.values()) {
                                    System.out.print(tag + " ");
                                }

                                System.out.print("\nSelect a category to view: ");
                                String strCategory = sc.nextLine().toUpperCase();
                                try {
                                    category = Category.valueOf(strCategory);
                                    break;
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Category doesn't exist");
                                }
                            }
                            manager.viewSpecificCategoryTransactions(category);

                            break;
                        case 2:
                            manager.viewAllCategoryTransactions();

                            break;
                        default:
                            break;
                    }

                    break;
                case 5:
                    manager.viewSummary();
                    break;
                case 6:
                    manager.saveToFile("personal_finance.json");
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    // Main function to run the application
    public static void main(String args[]) {
        App app = new App();
        app.run();
    }
}
