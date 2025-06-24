package FinanceTrackingSystemTests;

import FinanceSystem.FinanceTrackingSystem;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit tests to check the work with bank accounts")
public class BankAccountTests {
    private FinanceTrackingSystem finSystem;
    private FinanceTrackingSystemSteps steps;

    private static final String GENERAL_ACCOUNT = "General Bank Account";
    private static final String SAVING_ACCOUNT = "Saving";

    private static final int INCOME_AMOUNT = 1000;

    @BeforeEach
    @Step("Initialize FinanceTrackingSystem before each test")
    void init() {
        finSystem = new FinanceTrackingSystem();
        steps = new FinanceTrackingSystemSteps(finSystem);
    }

    @Test
    @DisplayName("Adding an bank account should add it to the list with available accounts")
    void testAddBankAccount() {
        steps.addBankAccount(SAVING_ACCOUNT);

        assertThat("The number of available accounts should increase",
                steps.getCountBankAccounts(), is(2));
    }

    @Test
    @DisplayName("Should throw when trying to create an existing account")
    void testAddDuplicateBankAccount() {
        steps.addBankAccount(SAVING_ACCOUNT);

        assertThrows(
                IllegalArgumentException.class,
                () -> steps.addBankAccount(SAVING_ACCOUNT)
        );
    }

    @Test
    @DisplayName("Deleting a bank account should remove it from the list of available accounts")
    void testDeleteBankAccount() {
        steps.addBankAccount(SAVING_ACCOUNT);
        steps.deleteBankAccount(SAVING_ACCOUNT);

        assertThat("The number of available accounts should reduce",
                steps.getCountBankAccounts(), is(1));
    }

    @Test
    @DisplayName("Deleting a bank account should remove it from the list of available accounts and transfer the balance")
    void testDeleteBankAccountWithBalance() {
        steps.addBankAccount(SAVING_ACCOUNT);
        steps.addIncome(SAVING_ACCOUNT, INCOME_AMOUNT);
        steps.deleteBankAccount(SAVING_ACCOUNT);

        assertThat("The number of available accounts should reduce",
                steps.getCountBankAccounts(), is(1));

        assertThat("The balance of the General Bank Account should increase by the amount of the deleted account",
                steps.getAccountBalance(GENERAL_ACCOUNT), is(INCOME_AMOUNT));

        assertThat("The current balance should not change",
                steps.getCurrentBalance(), is(INCOME_AMOUNT));
    }

    @Test
    @DisplayName("Should throw when deleting General Bank Account")
    void testDeleteGeneralBankAccount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> steps.deleteBankAccount(GENERAL_ACCOUNT)
        );
    }
}
