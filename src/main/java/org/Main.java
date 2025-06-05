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
                case "0" -> {
                    System.out.println("Bye!");
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
        System.out.println("0. Exit");
        System.out.print("Enter the item: ");
    }

    private static void showBalance() {
        System.out.println("\nCurrent balance: " + finSystem.getGeneralBalance());
        finSystem.showBankAccounts();
    }

    private static void addIncomeRecord() {
        if (finSystem.ListOfBankAccounts.size() > 1) {
            System.out.print("\nEnter the name of the income account: ");
            String nameIncomeAccount = scanner.nextLine().trim();
            System.out.print("\nEnter the amount of income: ");
            int amoutIncome = scanner.nextInt();
            finSystem.addIncome(nameIncomeAccount, amoutIncome);
        } else {
            System.out.print("\nEnter the income amount: ");
            finSystem.addIncome(scanner.nextInt());
        }
    }

    private static void addExpenseRecord() {
        if (finSystem.ListOfBankAccounts.size() > 1) {
            System.out.print("\nEnter the name of the expense account: ");
            String nameExpenseAccount = scanner.nextLine().trim();
            System.out.print("\nEnter the amount of expense: ");
            int amoutExpense = scanner.nextInt();
            finSystem.addExpense(nameExpenseAccount, amoutExpense);
        } else {
            System.out.print("\nEnter the amount of expense: ");
            finSystem.addExpense(scanner.nextInt());
        }
    }

    private static void workWithAccounts() {
        System.out.println("\n4.1. Add bank account");
        System.out.println("4.2. Delete bank account");
        System.out.println("4.3. Update bank account");

        switch (scanner.nextLine().trim()) {
            case "4.1" -> {
                return;
            }
            case "4.2" -> {
                return;
            }
            case "4.3" -> {
                return;
            }
            default -> System.out.println("Incorrect command selected! Try again");
        }
    }
}