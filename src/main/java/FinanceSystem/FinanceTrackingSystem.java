package FinanceSystem;

import FinanceSystem.FinanceParts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinanceTrackingSystem {
    private final BankAccount generalBankAccount;
    private final List<BankAccount> listOfBankAccounts;
    private final List<String> historyOfBankOperations = new ArrayList<>();
    private int historyId = 1;
    private final List<ExpenseCategory> listOfExpenseCategories = new ArrayList<>();

    public FinanceTrackingSystem() {
        this.generalBankAccount = new BankAccount("General Bank Account");
        this.listOfBankAccounts = new ArrayList<>();
        this.listOfBankAccounts.add(generalBankAccount);
    }

    public int getGeneralBalance() {
        return listOfBankAccounts.stream()
                .mapToInt(BankAccount::getAccountBalance)
                .sum();
    }

    public void addIncome(int income) {
        if (income <= 0) {
            throw new IllegalArgumentException("Income amount must be positive");
        }
        generalBankAccount.increaseBalance(income);
        addHistoryRecord("Income", income, generalBankAccount.getAccountName());
    }

    public void addIncome(String nameAccount, int income) {
        if (income <= 0) {
            throw new IllegalArgumentException("Income amount must be positive");
        }
        BankAccount account = findAccount(nameAccount)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        account.increaseBalance(income);
        addHistoryRecord("Income", income, account.getAccountName());
    }

    public void addExpense(int expense) {
        if (expense <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        generalBankAccount.reduceBalance(expense);
        addHistoryRecord("Expense", expense, generalBankAccount.getAccountName());
    }

    public void addExpense(String nameAccount, int expense) {
        if (expense <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        BankAccount account = findAccount(nameAccount)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        account.reduceBalance(expense);
        addHistoryRecord("Expense", expense, account.getAccountName());
    }

    public void addExpense(String nameAccount, int expense, String nameCategory) {
        if (expense <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        BankAccount account = findAccount(nameAccount)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        ExpenseCategory category = findCategory(nameCategory)
                .orElseThrow(() -> new IllegalArgumentException("Expense Category not found"));
        account.reduceBalance(expense);
        category.increaseExpense(expense);
        addHistoryRecord("Expense", expense, account.getAccountName());
    }

    public void addBankAccount(String accountName) {
        BankAccount bankAccount = new BankAccount(accountName);
        listOfBankAccounts.add(bankAccount);
    }

    public void deleteBankAccount(String accountName) {
        if (accountName.equals("General Bank Account"))
            throw new IllegalArgumentException("You can't delete General Bank Account");
        BankAccount account = findAccount(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));
        generalBankAccount.increaseBalance(account.getAccountBalance());
        addHistoryRecord("Income", account.getAccountBalance(),
                generalBankAccount.getAccountName() + " (" + account.getAccountName() + " deleted)");
        listOfBankAccounts.remove(account);
    }

    public int getListOfBankAccountsSize() {
        return listOfBankAccounts.size();
    }

    public void showBankAccounts() {
        for (BankAccount account : listOfBankAccounts) {
            System.out.printf(
                    "%s: %d%n",
                    account.getAccountName(),
                    account.getAccountBalance()
            );
        }
    }

    public void showBankHistory() {
        if (historyOfBankOperations.isEmpty()) {
            System.out.println("No finance transactions have been added yet!");
            return;
        }
        int startIndex = Math.max(0, historyOfBankOperations.size() - 10);
        for (int i = startIndex; i < historyOfBankOperations.size(); i++) {
            System.out.println(historyOfBankOperations.get(i));
        }
    }

    public void addExpenseCategory(String categoryName) {
        ExpenseCategory category = new ExpenseCategory(categoryName);
        listOfExpenseCategories.add(category);
    }

    public void deleteExpenseCategory(String categoryName) {
        if (listOfExpenseCategories.isEmpty()) {
            throw new IllegalStateException("No expense categories have been added yet");
        }
        ExpenseCategory category = findCategory(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Expense category not found"));
        listOfExpenseCategories.remove(category);
    }

    public int getListOfExpenseCategoriesSize() {
        return listOfExpenseCategories.size();
    }

    public void addLimitOfExpenseCategory(String categoryName, int limitAmount) {
        ExpenseCategory category = findCategory(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Expense category not found"));
        category.setLimitAmountOfExpenses(limitAmount);
    }

    public void clearAmountOfExpenseCategory(String categoryName) {
        ExpenseCategory category = findCategory(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Expense category not found"));
        category.clearAmountOfExpense();
    }

    public void showExpenditureStatistic() {
        if (listOfExpenseCategories.isEmpty()) {
            System.out.println("No expense categories have been added yet!");
            return;
        }
        for (ExpenseCategory category : listOfExpenseCategories) {
            System.out.printf(
                    "%s: %d%n",
                    category.getCategoryName(),
                    category.getAmountOfExpense()
            );
        }
    }

    private Optional<BankAccount> findAccount(String name) {
        return listOfBankAccounts.stream()
                .filter(acc -> acc.getAccountName().equals(name))
                .findFirst();
    }

    private Optional<ExpenseCategory> findCategory(String name) {
        return listOfExpenseCategories.stream()
                .filter(category -> category.getCategoryName().equals(name))
                .findFirst();
    }

    private void addHistoryRecord(String typeOperation, int amount, String accountName) {
        String record = String.format(
                "#%d: %s of %d on account '%s'",
                historyId++,
                typeOperation,
                amount,
                accountName
        );
        historyOfBankOperations.add(record);
    }
}
