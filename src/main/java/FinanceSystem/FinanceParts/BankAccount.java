package FinanceSystem.FinanceParts;

public class BankAccount {
    private final String accountName;
    private int accountBalance;

    public BankAccount(String accountName) {
        this.accountName = accountName;
        this.accountBalance = 0;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void increaseBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        accountBalance += amount;
    }

    public void reduceBalance(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (accountBalance < amount) {
            throw new IllegalStateException("Insufficient funds in the account");
        }
        accountBalance -= amount;
    }
}
