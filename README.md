# Automation QA Intership

This repository contains modules with automation QA internship homework assignments

> Created for educational purposes to learn unit testing and improve java programming skills

## Prerequisites:

-   Java 21.0.7
-   Gradle
-   IntelliJ IDEA (Community Edition)
-   Allure 2.34.0

---

## 1. Simple Finance Tracker

A simple console-based finance tracking application that allows you to monitor and analyze your income and expenses

### Project Structure

-   `FinanceTrackingSystem` — basic logic of the application, it contains all functional methods
-   `BankAccount` — represents a bank account, it contains the methods for working with its parameters — name and balance
-   `ExpenseCategory` — represents an expense category, it contains the methods for working with its parameters — name, expense amount and limit amount

### Installation

1. Clone this repository (or download ZIP)
2. Open the project in IntelliJ IDEA
3. Run the Main class (src/main/java/org/Main.java)

### How to use

Once the application is launched, a console menu will appear throughout its work. The application will prompt you for any required input

You can:

-   View current balance (with all bank accounts)
-   Add income or expenses
-   Manage bank accounts (add and delete)
-   Work with expense categories (add, delete, clear, set limits)
-   View expense statistics by category
-   View the history of the last 10 financial transactions

### How to view Allure reports:

1. Go to the command line: `Win+R`, `CMD`
2. Navigate to the root folder of the project using `cd`
3. Run the command: `allure open allure-report`

---

## 2. BO Highlights Manager UI Tests

This is an implementation of test UI scripts for BO Highlight Manager module

### Project Structure

-   `BackOffice` — the main package, it contains the `BackOfficePage` class with a description of the BO page structure and the `BackOfficeTests`, `BackOfficeSteps` classes with test scripts
-   `LoadConfig` — the helper package, it contains a `ConfigLoader` class that helps to get personal data from the configuration file
-   `LogIn` — the helper package, it contains a `LogInPage` class describing the structure of the authorisation page

Key features that have been tested:

-   adding, searching and deleting languages
-   sports management
-   management of sport events

### Installation

1. Clone this repository (or download ZIP)
2. Open the project in IntelliJ IDEA
3. Open the `UI-Test` module

### How to run tests

1. Add a configuration file named `config.properties` at the path: `UI-Tests/src/test/resources`
2. Add values for `username` and `password` variables to the configuration file
3. In IntelliJ IDEA open the Gradle window
4. Select UI-Tests
5. Select the task `Task/verification/test` and double-click on it

It is best to do the tests one at a time

### How to view Allure reports:

1. Go to the command line: `Win+R`, `CMD`
2. Navigate to the UI-Tests folder of the project using `cd`
3. Run the command: `allure open allure-report`

---

## 3. BO Highlights Manager REST API Tests

This is a REST API implementation of test scripts for the BO Highlight Manager module

### Project Structure

-   `clients` — this package contains `BackOfficeClient` and `FrontEndClient` classes for interaction with backend and frontend services
-   `ConfigReader` — helper package contains a class of the same name that reads user settings and parameters from the configuration file `config.properties`
-   `HighlightsManager` — main package contains classes for testing Highlights Manager functionality: `BackOfficeTests`, `FrontendTests` and `HighlightsManagerSteps`

API models were generated using Open API and are located in the `build` folder

Key features that have been tested:

-   adding and deleting languages
-   management of sport events and it's statuses
-   part of management of top sports

### Installation

1. Clone this repository (or download ZIP)
2. Open the project in IntelliJ IDEA
3. Open the `RestApiTests` module

### How to run tests

1. Add a configuration file named `config.properties` at the path: `RestApiTests/src/test/resources`
2. Add values for `username` and `password` variables to the configuration file
3. In IntelliJ IDEA open the Gradle window
4. Select RestApiTests
5. Select the task `Task/verification/test` and double-click on it

It is best to do the tests one at a time

### How to view Allure reports:

1. Go to the command line: `Win+R`, `CMD`
2. Navigate to the RestApiTests folder of the project using `cd`
3. Run the command: `allure open allure-report`

### How to view Swagger coverage report:

1. 