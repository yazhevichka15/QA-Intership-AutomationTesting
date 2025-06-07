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
    }

    private static void addExpenseRecord() {
        if (finSystem.getListOfBankAccountsSize() == 1) {
            System.out.print("\nEnter the amount of expense: ");
            int amountExpense = Integer.parseInt(scanner.nextLine());
            finSystem.addExpense(amountExpense);
        } else if (finSystem.getListOfBankAccountsSize() == 1 && finSystem.getListOfExpenseCategoriesSize() != 0) {
            System.out.print("Enter the amount of expense: ");
            int amountExpense = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the name of the expense category: ");
            String nameExpenseCategory = scanner.nextLine().trim();
            finSystem.addExpense("General Bank Account", amountExpense, nameExpenseCategory);
        } else if (finSystem.getListOfExpenseCategoriesSize() == 0) {
            System.out.print("\nEnter the name of the expense account: ");
            String nameExpenseAccount = scanner.nextLine().trim();
            System.out.print("Enter the amount of expense: ");
            int amountExpense = Integer.parseInt(scanner.nextLine());
            finSystem.addExpense(nameExpenseAccount, amountExpense);
        } else {
            System.out.print("\nEnter the name of the expense account: ");
            String nameExpenseAccount = scanner.nextLine().trim();
            System.out.print("Enter the amount of expense: ");
            int amountExpense = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the name of the expense category: ");
            String nameExpenseCategory = scanner.nextLine().trim();
            finSystem.addExpense(nameExpenseAccount, amountExpense, nameExpenseCategory);
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
                finSystem.deleteBankAccount(scanner.nextLine());
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
                System.out.print("\nEnter a name of expense category: ");
                String categoryName = scanner.nextLine().trim();
                System.out.print("Enter the amount of expense limit: ");
                int limitAmount = Integer.parseInt(scanner.nextLine());
                finSystem.addLimitOfExpenseCategory(categoryName, limitAmount);
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
                finSystem.deleteExpenseCategory(scanner.nextLine());
            }
            default -> System.out.println("Incorrect command selected! Try again");
        }
    }

    private static void showBankHistory() {
        finSystem.showBankHistory();
    }
}