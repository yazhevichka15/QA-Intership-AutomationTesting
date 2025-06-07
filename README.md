# Simple Finance Tracker
A simple console-based finance tracking application that allows you to monitor and analyze your income and expenses


> Created for educational purposes to learn unit testing and improve java programming skills

## Installation

---
This project uses **Java 21.0.7** and the **Gradle** build system

### Prerequisites:
- Java 21.0.7
- Gradle
- IntelliJ IDEA (Community Edition)

### Steps:
1. Clone this repository (or download ZIP)
2. Open the project in IntelliJ IDEA
3. Run the Main class (src/main/java/org/Main.java)

## How to use

---

Once the application is launched, a console menu will appear throughout its work

You can:
- View current balance (with all bank accounts)
- Add income or expenses
- Manage bank accounts (add and delete)
- Work with expense categories (add, delete, clear, set limits)
- View expense statistics by category
- View the history of the last 10 financial transactions

The application will prompt you for any required input

## Project Structure

---

This will describe the main structural classes and their methods that are in the application code

- `FinanceTrackingSystem` — basic logic of the application, it contains all functional methods
- `BankAccount` — represents a bank account, it contains the methods for working with its parameters — name and balance
- `ExpenseCategory` — represents an expense category, it contains the methods for working with its parameters — name, expense amount and limit amount


