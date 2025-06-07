package FinanceSystem.FinanceParts;

public class ExpenseCategory {
    private final String categoryName;
    private int amountOfExpense = 0;
    private int limitAmountOfExpenses = -1;

    public ExpenseCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getAmountOfExpense() {
        return amountOfExpense;
    }

    public void increaseExpense(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        if (limitAmountOfExpenses != -1 && (amountOfExpense + amount > limitAmountOfExpenses)) {
            throw new IllegalArgumentException("Expense amount exceeds the set limit");
        }
        amountOfExpense += amount;
    }

    public void clearAmountOfExpense() {
        amountOfExpense = 0;
    }

    public void setLimitAmountOfExpenses(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Limit amount must be positive");
        }
        if (limitAmountOfExpenses != -1) {
            throw new IllegalStateException("Limit already has been set for this category");
        }
        if (amountOfExpense > amount) {
            throw new IllegalStateException("Current expenses exceed the limit you're trying to set");
        }
        this.limitAmountOfExpenses = amount;
    }
}
