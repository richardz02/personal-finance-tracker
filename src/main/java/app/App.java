package app;

import manager.TransactionManager;
import model.Category;
import model.Transaction;
import model.Type;

import java.util.Scanner;

public class App {

    public void run() {
        // Create a transaction manager
        TransactionManager manager = new TransactionManager();

        while (true) {
            // Print out menu
            System.out.println("===== PERSONAL FINANCE TRACKER =====");
            System.out.println("1. Add income");
            System.out.println("2. Add expense");
            System.out.println("3. View all transactions");
            System.out.println("4. View by category");
            System.out.println("5, View summary");
            System.out.println("6. Save and exit");
            System.out.println("====================================");

            Scanner sc = new Scanner(System.in);
            int action = Integer.parseInt(sc.nextLine());

            switch(action) {
                case 1:
                    {
                        System.out.print("Enter amount ($): ");
                        double amount = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter description: ");
                        String description = sc.nextLine();

                        System.out.print("Select category ( ");
                        for (Category category : Category.values()) {
                            System.out.print(category + " ");
                        }
                        System.out.print("): ");
                        String tag = sc.nextLine().toUpperCase();
                        Category category = Category.valueOf(tag);

                        Transaction newTransaction = new Transaction(amount, Type.INCOME, category, description);
                        manager.addTransaction(newTransaction);

                        System.out.println("Added transaction");
                    }

                    break;
                case 2:
                    {
                        System.out.print("Enter amount ($): ");
                        double amount = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter description: ");
                        String description = sc.nextLine();

                        System.out.print("Select category ( ");
                        for (Category category : Category.values()) {
                            System.out.print(category + " ");
                        }
                        System.out.print("): ");
                        String tag = sc.nextLine().toUpperCase();
                        Category category = Category.valueOf(tag);

                        Transaction newTransaction = new Transaction(amount, Type.EXPENSE, category, description);
                        manager.addTransaction(newTransaction);

                        System.out.println("Added transaction");
                    }

                    break;
                case 3:
                    if (manager.isEmpty()) {
                        System.out.println("No transactions");
                        continue;
                    }

                    manager.printTransactions();

                    break;
                case 4:

                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input, try again");
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
