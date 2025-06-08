import FinanceSystem.*;

import java.util.List;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit tests for FinanceTrackingSystem")
class FinanceTrackingSystemTest {

    private FinanceTrackingSystem finSystem;

    @BeforeEach
    @Step("Initialize FinanceTrackingSystem before each test")
    void init() {
        finSystem = new FinanceTrackingSystem();
    }

    @Test
    @DisplayName("Adding a income should increase the current balance")
    void testAddIncome() {
        addIncome(1);

        assertThat("Current balance should increase by the input",
                getCurrentBalance(), is(1));

        assertThat("General Bank Account balance should increase by the input",
                getAccountBalance("General Bank Account"), is(1));
    }

    @Test
    @DisplayName("Should throw exception when income is non-positive")
    void testAddIncomeWithInvalidAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> addIncome(0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> addIncome(-1)
        );
    }

    @Test
    @DisplayName("Adding a income to another account should increase the current balance and the account's balance")
    void testAddIncomeToAnotherAccount() {
        addBankAccount("Saving");
        addIncome("Saving", 100);

        assertThat("Current balance should increase by the input",
                getCurrentBalance(), is(100));

        assertThat("Bank Account balance should increase by the input",
                getAccountBalance("Saving"), is(100));

        assertThat("General Bank Account balance should not increase",
                getAccountBalance("General Bank Account"), is(0));
    }

    @Test
    @DisplayName("Adding an expense should reduce the current balance")
    void testAddExpanse() {
        addIncome(100);
        addExpense(80);

        assertThat("Current balance should reduce by the input",
                getCurrentBalance(), is(20));

        assertThat("General Bank Account balance should reduce by the input",
                getAccountBalance("General Bank Account"), is(20));
    }

    @Test
    @DisplayName("Should throw exception when expense is non-positive")
    void testAddExpanseWithInvalidInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> addExpense(0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> addExpense(-1)
        );
    }

    @Test
    @DisplayName("Adding a expense to another account should reduce the current balance and the account's balance")
    void testAddExpanseToAnotherAccount() {
        addBankAccount("Test");
        addIncome("Test", 100);
        addExpense("Test", 80);

        assertThat("Current balance should reduce by the input",
                getCurrentBalance(), is(20));

        assertThat("Bank Account balance should reduce by the input",
                getAccountBalance("Test"), is(20));
    }

    @Test
    @DisplayName("Adding expenses to a category should reduce the current balance and increase the amount in the category")
    void testAddExpanseToCategory() {
        addIncome(100);
        addCategory("Food");
        addExpense("General Bank Account", 80, "Food");

        assertThat("Amount of expense category should increase by the input",
                getCategoryExpense("Food"), is(80));

        assertThat("General Bank Account balance should reduce by the input",
                getAccountBalance("General Bank Account"), is(20));
    }

    @Test
    @DisplayName("Should throw an exception when the expense exceeds the limit amount")
    void testAddExpanseExceedsLimit() {
        addCategory("Transport");
        addLimitOfCategory("Transport", 100);

        assertThrows(
                IllegalArgumentException.class,
                () -> addExpense(101)
        );
    }
//
//    @Test
//    @DisplayName("")
//    void testAddBankAccount() {}
//
//    @Test
//    @DisplayName("")
//    void testAddDuplicateBankAccount() {}
//
//    @Test
//    @DisplayName("")
//    void testDeleteBankAccount() {}
//
//    @Test
//    @DisplayName("")
//    void testDeleteGeneralBankAccount() {}
//
//    @Test
//    @DisplayName("")
//    void testShowBankHistory() {}
//
//    @Test
//    @DisplayName("")
//    void testEmptyShowBankHistory() {}
//
//    @Test
//    @DisplayName("")
//    void testAddExpenseCategory() {
//    assertThat("",
//                finSystem.getListOfExpenseCategoriesSize(), is(1));}
//
//    @Test
//    @DisplayName("")
//    void testAddLimitToExpenseCategory() {}
//
//    @Test
//    @DisplayName("")
//    void testAddDuplicateLimitToExpenseCategory() {}






    @Step("Add income of {amount} to General Bank Account")
    private void addIncome(int amount) {
        finSystem.addIncome(amount);
    }

    @Step("Add income of {amount} to account {account}")
    private void addIncome(String account, int amount) {
        finSystem.addIncome(account, amount);
    }

    @Step("Add expense of {amount} to General Bank Account")
    private void addExpense(int amount) {
        finSystem.addExpense(amount);
    }

    @Step("Add expense of {amount} to account {account}")
    private void addExpense(String account, int amount) {
        finSystem.addExpense(account, amount);
    }

    @Step("Add expense of {amount} to account {account} in category {category}")
    private void addExpense(String account, int amount, String category) {
        finSystem.addExpense(account, amount, category);
    }

    @Step("Get balance of account {accountName}")
    private int getCurrentBalance() {
        return finSystem.getGeneralBalance();
    }

    @Step("Get balance of account {accountName}")
    private int getAccountBalance(String accountName) {
        return finSystem.getBalanceOfBankAccount(accountName);
    }

    @Step("Add bank account {accountName}")
    private void addBankAccount(String accountName) {
        finSystem.addBankAccount(accountName);
    }

    @Step("Add expense category {categoryName}")
    private void addCategory(String categoryName) {
        finSystem.addExpenseCategory(categoryName);
    }

    @Step("Get expense amount of category {categoryName}")
    private int getCategoryExpense(String categoryName) {
        return finSystem.getExpenseAmountOfCategory(categoryName);
    }

    @Step("Add limit to expense category {categoryName} amount of limit {limit}")
    private void addLimitOfCategory(String categoryName, int limit) {
        finSystem.addLimitOfExpenseCategory(categoryName, limit);
    }

}
