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
        account.increaseBalance(expense);
        addHistoryRecord("Expense", expense, account.getAccountName());
    }

    public void addBankAccount(String accountName) {
        BankAccount bankAccount = new BankAccount(accountName);
        listOfBankAccounts.add(bankAccount);
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

    private Optional<BankAccount> findAccount(String name) {
        return listOfBankAccounts.stream()
                .filter(acc -> acc.getAccountName().equals(name))
                .findFirst();
    }

    private void addHistoryRecord(String typeOperation, int amount, String accountName) {
        String record = String.format(
                "№%d: %s of %d on account '%s'",
                historyId++,
                typeOperation,
                amount,
                accountName
        );
        historyOfBankOperations.add(record);
    }
}
