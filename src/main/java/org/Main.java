package org;

import FinanceSystem.FinanceTrackingSystem;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FinanceTrackingSystem finSystem = new FinanceTrackingSystem();

    public static void main(String[] args) {
        printWelcomeMessage();
        while (true) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> showBalance();
                case "2" -> addIncomeRecord();
                case "3" -> addExpenseRecord();
                case "4" -> workWithAccounts();
                case "5" -> workWithFinanceCategories();
                case "6" -> showBankHistory();
                case "0" -> {
                    System.out.println("Bye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Incorrect command selected! Try again");
            }
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to the Simple Finance Tracker!");
        System.out.println("Select an item from the menu to get started");
    }

    private static void printMenu() {
        System.out.println("\n===== Menu =====");
        System.out.println("1. Show current balance");
        System.out.println("2. Add income record");
        System.out.println("3. Add expense record");
        System.out.println("4. Working with bank accounts");
        System.out.println("5. Working with finance category of expense");
        System.out.println("6. Show history of bank operations");
        System.out.println("0. Exit");
        System.out.print("Enter the item: ");
    }

    private static void showBalance() {
        System.out.println("\nCurrent balance: " + finSystem.getGeneralBalance());
        finSystem.showBankAccounts();
    }

    private static void addIncomeRecord() {
        try {
            if (finSystem.getListOfBankAccountsSize() == 1) {
                System.out.print("\nEnter the income amount: ");
                int amountIncome = Integer.parseInt(scanner.nextLine());
                finSystem.addIncome(amountIncome);
            } else {
                System.out.print("\nEnter the name of the income account: ");
                String nameIncomeAccount = scanner.nextLine().trim();
                System.out.print("Enter the amount of income: ");
                int amountIncome = Integer.parseInt(scanner.nextLine());
                finSystem.addIncome(nameIncomeAccount, amountIncome);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addExpenseRecord() {
        try {
            String accountName;
            if (finSystem.getListOfBankAccountsSize() == 1) {
                accountName = "General Bank Account";
            } else {
                System.out.print("\nEnter the name of the expense account: ");
                accountName = scanner.nextLine().trim();
            }
            System.out.print("Enter the amount of expense: ");
            int amountExpense = Integer.parseInt(scanner.nextLine());
            if (finSystem.getListOfExpenseCategoriesSize() > 0) {
                System.out.print("Enter the name of the expense category (or leave empty to skip): ");
                String categoryName = scanner.nextLine().trim();
                if (!categoryName.isEmpty()) {
                    finSystem.addExpense(accountName, amountExpense, categoryName);
                    return;
                }
            }
            if (accountName.equals("General Bank Account")) {
                finSystem.addExpense(amountExpense);
            } else {
                finSystem.addExpense(accountName, amountExpense);
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void workWithAccounts() {
        System.out.println("\n4.1. Add bank account");
        System.out.println("4.2. Delete bank account");
        System.out.print("Enter the item: ");

        switch (scanner.nextLine().trim()) {
            case "4.1" -> {
                System.out.print("\nEnter a name for the new bank account: ");
                finSystem.addBankAccount(scanner.nextLine());
            }
            case "4.2" -> {
                System.out.print("\nEnter the name of the bank account to be deleted: ");
                try {
                    finSystem.deleteBankAccount(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            default -> System.out.println("Incorrect command selected! Try again");
        }
    }

    private static void workWithFinanceCategories() {
        System.out.println("\n5.1. Add expense category");
        System.out.println("5.2. Add a limit for expense category");
        System.out.println("5.3. Clear the current amount in the category");
        System.out.println("5.4. Show statistic on expenditure");
        System.out.println("5.5. Delete expense category");
        System.out.print("Enter the item: ");

        switch (scanner.nextLine().trim()) {
            case "5.1" -> {
                System.out.print("\nEnter a name for new expense category: ");
                finSystem.addExpenseCategory(scanner.nextLine());
            }
            case "5.2" -> {
                try {
                    System.out.print("\nEnter a name of expense category: ");
                    String categoryName = scanner.nextLine().trim();
                    System.out.print("Enter the amount of expense limit: ");
                    int limitAmount = Integer.parseInt(scanner.nextLine());
                    finSystem.addLimitOfExpenseCategory(categoryName, limitAmount);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case "5.3" -> {
                System.out.print("\nEnter a name expense category: ");
                finSystem.clearAmountOfExpenseCategory(scanner.nextLine());
            }
            case "5.4" -> {
                finSystem.showExpenditureStatistic();
            }
            case "5.5" -> {
                System.out.print("\nEnter a name expense category for delete : ");
                try {
                    finSystem.deleteExpenseCategory(scanner.nextLine());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            default -> System.out.println("Incorrect command selected! Try again");
        }
    }

    private static void showBankHistory() {
        finSystem.showBankHistory();
    }
}