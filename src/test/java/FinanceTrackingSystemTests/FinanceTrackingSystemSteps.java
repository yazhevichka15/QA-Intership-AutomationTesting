package FinanceTrackingSystemTests;

import FinanceSystem.FinanceTrackingSystem;
import io.qameta.allure.Step;

public class FinanceTrackingSystemSteps {
    private final FinanceTrackingSystem finSystem;

    public FinanceTrackingSystemSteps(FinanceTrackingSystem finSystem) {
        this.finSystem = finSystem;
    }

    @Step("Get the general total current balance")
    public int getCurrentBalance() {
        return finSystem.getGeneralBalance();
    }

    @Step("Get balance of account {accountName}")
    public int getAccountBalance(String accountName) {
        return finSystem.getBalanceOfBankAccount(accountName);
    }

    @Step("Add income of {amount} to General Bank Account")
    public void addIncome(int amount) {
        finSystem.addIncome(amount);
    }

    @Step("Add income of {amount} to account {account}")
    public void addIncome(String account, int amount) {
        finSystem.addIncome(account, amount);
    }

    @Step("Add expense of {amount} to General Bank Account")
    public void addExpense(int amount) {
        finSystem.addExpense(amount);
    }

    @Step("Add expense of {amount} to account {account}")
    public void addExpense(String account, int amount) {
        finSystem.addExpense(account, amount);
    }

    @Step("Add expense of {amount} to account {account} in category {category}")
    public void addExpense(String account, int amount, String category) {
        finSystem.addExpense(account, amount, category);
    }

    @Step("Add bank account {accountName}")
    public void addBankAccount(String accountName) {
        finSystem.addBankAccount(accountName);
    }

    @Step("Delete bank account {accountName}")
    public void deleteBankAccount(String accountName) {
        finSystem.deleteBankAccount(accountName);
    }

    @Step("Get the current number of available bank accounts")
    public int getCountBankAccounts() {
        return finSystem.getListOfBankAccountsSize();
    }

    @Step("Add expense category {categoryName}")
    public void addCategory(String categoryName) {
        finSystem.addExpenseCategory(categoryName);
    }

    @Step("Get expense amount of category {categoryName}")
    public int getCategoryExpense(String categoryName) {
        return finSystem.getExpenseAmountOfCategory(categoryName);
    }

    @Step("Add limit amount of limit {limit} to expense category {categoryName}")
    public void addLimitOfCategory(String categoryName, int limit) {
        finSystem.addLimitOfExpenseCategory(categoryName, limit);
    }
}
