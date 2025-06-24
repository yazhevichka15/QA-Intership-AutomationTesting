package FinanceTrackingSystemTests;

import FinanceSystem.FinanceTrackingSystem;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Unit tests to check the work with bank history")
public class BankHistoryTests {
    private FinanceTrackingSystem finSystem;
    private FinanceTrackingSystemSteps steps;

    private static final int INCOME_AMOUNT = 1000;
    private static final int EXPENSE_AMOUNT = 250;

    private static final String GENERAL_ACCOUNT = "General Bank Account";
    private static final String INCOME_RECORD =
            "#1: Income of " + INCOME_AMOUNT + " on account '" + GENERAL_ACCOUNT + "'";
    private static final String EXPENSE_RECORD =
            "#2: Expense of " + EXPENSE_AMOUNT + " on account '" + GENERAL_ACCOUNT + "'";

    @BeforeEach
    @Step("Initialize FinanceTrackingSystem before each test")
    void init() {
        finSystem = new FinanceTrackingSystem();
        steps = new FinanceTrackingSystemSteps(finSystem);
    }

    @Test
    @DisplayName("Bank history should show income and expense records")
    void testShowBankHistory() {
        steps.addIncome(INCOME_AMOUNT);
        steps.addExpense(EXPENSE_AMOUNT);

        List<String> history = finSystem.getBankHistory();

        assertThat("The history should contain 2 transaction records",
                history, hasSize(2));

        assertThat("First record should contain information about the income input",
                history.get(0), is(INCOME_RECORD));

        assertThat("Second record should contain information about the expense input",
                history.get(1), is(EXPENSE_RECORD));
    }

    @Test
    @DisplayName("Bank history should be empty when there no transactions")
    void testEmptyShowBankHistory() {
        List<String> history = finSystem.getBankHistory();

        assertThat("Bank history should be empty",
                history, is(empty()));
    }
}
