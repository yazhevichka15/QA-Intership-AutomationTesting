import FinanceSystem.*;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Unit tests for FinanceTrackingSystem")
class FinanceTrackingSystemTest {

    private FinanceTrackingSystem finSystem;

    @BeforeEach
    @Step("Initialize FinanceTrackingSystem before each test")
    void init() {
        finSystem = new FinanceTrackingSystem();
    }

    @Nested
    @DisplayName("Income Tests")
    class IncomeTests {
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
    }

    @Nested
    @DisplayName("Expense Tests")
    class ExpanseTests {
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

            assertThat("Bank account balance should reduce by the input",
                    getAccountBalance("Test"), is(20));

            assertThat("General Bank Account balance should not change",
                    getAccountBalance("General Bank Account"), is(0));
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
    }

    @Nested
    @DisplayName("Bank Account Tests")
    class BankAccountTests {
        @Test
        @DisplayName("Adding an bank account should add it to the list with available accounts")
        void testAddBankAccount() {
            addBankAccount("Saving");

            assertThat("The number of available accounts should increase",
                    getCountBankAccounts(), is(2));
        }

        @Test
        @DisplayName("Should throw when trying to create an existing account")
        void testAddDuplicateBankAccount() {
            addBankAccount("Saving");

            assertThrows(
                    IllegalArgumentException.class,
                    () -> addBankAccount("Saving")
            );
        }

        @Test
        @DisplayName("Deleting a bank account should remove it from the list of available accounts")
        void testDeleteBankAccount() {
            addBankAccount("Saving");
            deleteBankAccount("Saving");

            assertThat("The number of available accounts should reduce",
                    getCountBankAccounts(), is(1));
        }

        @Test
        @DisplayName("Deleting a bank account should remove it from the list of available accounts and transfer the balance")
        void testDeleteBankAccountWithBalance() {
            addBankAccount("Saving");
            addIncome("Saving", 1000);
            deleteBankAccount("Saving");

            assertThat("The number of available accounts should reduce",
                    getCountBankAccounts(), is(1));

            assertThat("The balance of the General Bank Account should increase by the amount of the deleted account",
                    getAccountBalance("General Bank Account"), is(1000));

            assertThat("The current balance should not change",
                    getCurrentBalance(), is(1000));
        }

        @Test
        @DisplayName("Should throw when deleting General Bank Account")
        void testDeleteGeneralBankAccount() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> deleteBankAccount("General Bank Account")
            );
        }
    }

    @Nested
    @DisplayName("Bank History Tests")
    class BankHistoryTests {
        @Test
        @DisplayName("Bank history should show income and expense records")
        void testShowBankHistory() {
            addIncome(1000);
            addExpense(250);

            List<String> history = finSystem.getBankHistory();

            assertThat("The history should contain 2 transation records",
                    history, hasSize(2));

            assertThat("First record should contain information about the income input",
                    history.get(0), is("#1: Income of 1000 on account 'General Bank Account'"));

            assertThat("Second record should contain information about the expense input",
                    history.get(1), is("#2: Expense of 250 on account 'General Bank Account'"));
        }

        @Test
        @DisplayName("Bank history should be empty when there no transactions")
        void testEmptyShowBankHistory() {
            List<String> history = finSystem.getBankHistory();

            assertThat("Bank history should be empty",
                    history, is(empty()));
        }
    }

    @Step("Get the general total current balance")
    private int getCurrentBalance() {
        return finSystem.getGeneralBalance();
    }

    @Step("Get balance of account {accountName}")
    private int getAccountBalance(String accountName) {
        return finSystem.getBalanceOfBankAccount(accountName);
    }

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

    @Step("Add bank account {accountName}")
    private void addBankAccount(String accountName) {
        finSystem.addBankAccount(accountName);
    }

    @Step("Delete bank account {accountName}")
    private void deleteBankAccount(String accountName) {
        finSystem.deleteBankAccount(accountName);
    }

    @Step("Get the current number of available bank accounts")
    private int getCountBankAccounts() {
        return finSystem.getListOfBankAccountsSize();
    }

    @Step("Add expense category {categoryName}")
    private void addCategory(String categoryName) {
        finSystem.addExpenseCategory(categoryName);
    }

    @Step("Get expense amount of category {categoryName}")
    private int getCategoryExpense(String categoryName) {
        return finSystem.getExpenseAmountOfCategory(categoryName);
    }

    @Step("Add limit amount of limit {limit} to expense category {categoryName}")
    private void addLimitOfCategory(String categoryName, int limit) {
        finSystem.addLimitOfExpenseCategory(categoryName, limit);
    }
}
