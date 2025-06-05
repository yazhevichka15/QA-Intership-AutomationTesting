package FinanceSystem;
import FinanceSystem.FinanceParts.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FinanceTrackingSystem {
    private int GeneralBalance;
    private BankAccount GeneralBankAccount;
    public List<BankAccount> ListOfBankAccounts;
    private List<String> HistoryOfBankOperations = new ArrayList<>();

    public FinanceTrackingSystem() {
        this.GeneralBalance = 0;
        this.GeneralBankAccount = new BankAccount("General Bank Account");
        this.ListOfBankAccounts = new ArrayList<>();
        this.ListOfBankAccounts.add(GeneralBankAccount);
    }

    public int getGeneralBalance() {
        return GeneralBalance;
    }

    public void addIncome(int income) {
        GeneralBalance += income;
        GeneralBankAccount.AccountBalance += income;
    }

    public void addIncome(String nameAccount, int income) {
        GeneralBalance += income;
        for (BankAccount account : ListOfBankAccounts) {
            if (account.AccountName == nameAccount) {
                account.AccountBalance += income;
                break;
            };
        }
    }

    public void addExpense(int expense) {
        GeneralBalance -= expense;
        GeneralBankAccount.AccountBalance -= expense;
    }

    public void addExpense(String nameAccount, int expense) {
        GeneralBalance -= expense;
        for (BankAccount account : ListOfBankAccounts) {
            if (account.AccountName == nameAccount) {
                account.AccountBalance -= expense;
                break;
            };
        }
    }

    public void addBankAccount(String accountName) {
        BankAccount bankAccount = new BankAccount(accountName);
        ListOfBankAccounts.add(bankAccount);
    }

    public void showBankAccounts() {
        for (BankAccount account : ListOfBankAccounts) {
            System.out.println(account.AccountName + ": " + account.AccountBalance);
        }
    }
}
