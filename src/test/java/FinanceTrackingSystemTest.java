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
    @DisplayName("Adding a income should increase the current balance and the general bank account balance")
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

//    @Test
//    @DisplayName("")
//    void testAddExpanse() {
//    }
//
//    @Test
//    @DisplayName("")
//    void testAddExpanseWithInvalidInput() {
//    }
//
//    @Test
//    @DisplayName("")
//    void testAddExpanseToAnotherAccount() {
//    }
//
//    @Test
//    @DisplayName("")
//    void testAddExpanseToCategory() {
//
//    }
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
//    void testAddExpenseCategory() {}
//
//    @Test
//    @DisplayName("")
//    void testAddLimitToExpenseCategory() {}
//
//    @Test
//    @DisplayName("")
//    void testAddDuplicateLimitToExpenseCategory() {}






    @Step("Add income of {amount} to account General Bank Account")
    private void addIncome(int amount) {
        finSystem.addIncome(amount);
    }

    @Step("Add income of {amount} to account {account}")
    private void addIncome(String account, int amount) {
        finSystem.addIncome(account, amount);
    }

    @Step("Get balance of account {accountName}")
    private int getCurrentBalance() {
        return finSystem.getGeneralBalance();
    }

    @Step("Get balance of account {accountName}")
    private int getAccountBalance(String accountName) {
        return finSystem.getBalanceOfBankAccount(accountName);
    }

    @Step("Yes")
    private void addBankAccount(String accountName) {
        finSystem.addBankAccount(accountName);
    }
}
