import java.io.*;
import java.util.*;

// Expense Class
class Expense {
    double amount;
    String category;
    String date;
    String description;

    Expense(double amount, String category, String date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public String toString() {
        return amount + "," + category + "," + date + "," + description;
    }
}

// Expense Manager Class
class ExpenseManager {
    ArrayList<Expense> expenses = new ArrayList<>();
    final String FILE_NAME = "expenses.txt";

    void addExpense(Expense e) {
        expenses.add(e);
        saveToFile(e);
    }

    void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        for (Expense e : expenses) {
            System.out.println("Amount: " + e.amount + " | Category: " + e.category +
                    " | Date: " + e.date + " | Description: " + e.description);
        }
    }

    void getTotalExpense() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.println("Total Expense: " + total);
    }

    void filterByCategory(String category) {
        boolean found = false;
        for (Expense e : expenses) {
            if (e.category.equalsIgnoreCase(category)) {
                System.out.println(e.amount + " | " + e.description + " | " + e.date);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No expenses found in this category.");
        }
    }

    void saveToFile(Expense e) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(e.toString() + "\n");
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error saving file.");
        }
    }

    void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    double amt = Double.parseDouble(parts[0]);
                    String cat = parts[1];
                    String date = parts[2];
                    String desc = parts[3];

                    expenses.add(new Expense(amt, cat, date, desc));
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}

// MAIN CLASS (NAME MATCHES FILE NAME)
public class expensetracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        manager.loadFromFile();

        while (true) {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Expense");
            System.out.println("4. Filter by Category");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Category: ");
                    String cat = sc.nextLine();

                    System.out.print("Date: ");
                    String date = sc.nextLine();

                    System.out.print("Description: ");
                    String desc = sc.nextLine();

                    manager.addExpense(new Expense(amt, cat, date, desc));
                    System.out.println("Expense added!");
                    break;

                case 2:
                    manager.viewExpenses();
                    break;

                case 3:
                    manager.getTotalExpense();
                    break;

                case 4:
                    System.out.print("Enter category: ");
                    String c = sc.nextLine();
                    manager.filterByCategory(c);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
