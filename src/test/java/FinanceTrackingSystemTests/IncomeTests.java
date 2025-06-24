package FinanceTrackingSystemTests;

import FinanceSystem.FinanceTrackingSystem;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit tests to check the work with incomes")
public class IncomeTests {
    private FinanceTrackingSystem finSystem;
    private FinanceTrackingSystemSteps steps;

    private static final int VALID_INCOME = 1;
    private static final int ZERO_INCOME = 0;
    private static final int NEGATIVE_INCOME = -1;
    private static final int CUSTOM_INCOME = 100;

    private static final String GENERAL_ACCOUNT = "General Bank Account";
    private static final String SAVING_ACCOUNT = "Saving";

    @BeforeEach
    @Step("Initialize FinanceTrackingSystem before each test")
    void init() {
        finSystem = new FinanceTrackingSystem();
        steps = new FinanceTrackingSystemSteps(finSystem);
    }

    @Test
    @DisplayName("Adding a income should increase the current balance")
    void testAddIncome() {
        steps.addIncome(VALID_INCOME);

        assertThat("Current balance should increase by the input",
                steps.getCurrentBalance(), is(VALID_INCOME));

        assertThat("General Bank Account balance should increase by the input",
                steps.getAccountBalance(GENERAL_ACCOUNT), is(VALID_INCOME));
    }

    @Test
    @DisplayName("Should throw exception when income is non-positive")
    void testAddIncomeWithInvalidAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> steps.addIncome(ZERO_INCOME)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> steps.addIncome(NEGATIVE_INCOME)
        );
    }

    @Test
    @DisplayName("Adding a income to another account should increase the current balance and the account's balance")
    void testAddIncomeToAnotherAccount() {
        steps.addBankAccount(SAVING_ACCOUNT);
        steps.addIncome(SAVING_ACCOUNT, CUSTOM_INCOME);

        assertThat("Current balance should increase by the input",
                steps.getCurrentBalance(), is(CUSTOM_INCOME));

        assertThat("Bank Account balance should increase by the input",
                steps.getAccountBalance(SAVING_ACCOUNT), is(CUSTOM_INCOME));

        assertThat("General Bank Account balance should not increase",
                steps.getAccountBalance(GENERAL_ACCOUNT), is(0));
    }
}
