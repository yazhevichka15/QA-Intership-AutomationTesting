package FinanceTrackingSystemTests;

import FinanceSystem.FinanceTrackingSystem;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit tests to check the work with expenses")
public class ExpenseTests {
    private FinanceTrackingSystem finSystem;
    private FinanceTrackingSystemSteps steps;

    private static final int VALID_INCOME = 100;
    private static final int VALID_EXPENSE = 80;
    private static final int EXPECTED_BALANCE = 20;
    private static final int ZERO_EXPENSE = 0;
    private static final int NEGATIVE_EXPENSE = -1;
    private static final int LIMIT_AMOUNT = 100;
    private static final int EXPENSE_ABOVE_LIMIT = 101;

    private static final String GENERAL_ACCOUNT = "General Bank Account";
    private static final String TEST_ACCOUNT = "Test";
    private static final String FOOD_CATEGORY = "Food";
    private static final String TRANSPORT_CATEGORY = "Transport";

    @BeforeEach
    @Step("Initialize FinanceTrackingSystem before each test")
    void init() {
        finSystem = new FinanceTrackingSystem();
        steps = new FinanceTrackingSystemSteps(finSystem);
    }

    @Test
    @DisplayName("Adding an expense should reduce the current balance")
    void testAddExpense() {
        steps.addIncome(VALID_INCOME);
        steps.addExpense(VALID_EXPENSE);

        assertThat("Current balance should reduce by the input",
                steps.getCurrentBalance(), is(EXPECTED_BALANCE));

        assertThat("General Bank Account balance should reduce by the input",
                steps.getAccountBalance(GENERAL_ACCOUNT), is(EXPECTED_BALANCE));
    }

    @Test
    @DisplayName("Should throw exception when expense is non-positive")
    void testAddExpenseWithInvalidInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> steps.addExpense(ZERO_EXPENSE)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> steps.addExpense(NEGATIVE_EXPENSE)
        );
    }

    @Test
    @DisplayName("Adding a expense to another account should reduce the current balance and the account's balance")
    void testAddExpenseToAnotherAccount() {
        steps.addBankAccount(TEST_ACCOUNT);
        steps.addIncome(TEST_ACCOUNT, VALID_INCOME);
        steps.addExpense(TEST_ACCOUNT, VALID_EXPENSE);

        assertThat("Current balance should reduce by the input",
                steps.getCurrentBalance(), is(EXPECTED_BALANCE));

        assertThat("Bank account balance should reduce by the input",
                steps.getAccountBalance(TEST_ACCOUNT), is(EXPECTED_BALANCE));

        assertThat("General Bank Account balance should not change",
                steps.getAccountBalance(GENERAL_ACCOUNT), is(0));
    }

    @Test
    @DisplayName("Adding expenses to a category should reduce the current balance and increase the amount in the category")
    void testAddExpenseToCategory() {
        steps.addIncome(VALID_INCOME);
        steps.addCategory(FOOD_CATEGORY);
        steps.addExpense(GENERAL_ACCOUNT, VALID_EXPENSE, FOOD_CATEGORY);

        assertThat("Amount of expense category should increase by the input",
                steps.getCategoryExpense(FOOD_CATEGORY), is(EXPECTED_BALANCE));

        assertThat("General Bank Account balance should reduce by the input",
                steps.getAccountBalance(GENERAL_ACCOUNT), is(EXPECTED_BALANCE));
    }

    @Test
    @DisplayName("Should throw an exception when the expense exceeds the limit amount")
    void testAddExpenseExceedsLimit() {
        steps.addCategory(TRANSPORT_CATEGORY);
        steps.addLimitOfCategory(TRANSPORT_CATEGORY, LIMIT_AMOUNT);

        assertThrows(
                IllegalArgumentException.class,
                () -> steps.addExpense(EXPENSE_ABOVE_LIMIT)
        );
    }
}
