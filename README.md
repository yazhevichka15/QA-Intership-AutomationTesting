# Automation QA Intership

This repository contains modules with automation QA internship homework assignments

> Created for educational purposes to learn unit testing and improve java programming skills

## Used stack:

-   Java 21
-   Gradle
-   IntelliJ IDEA (Community Edition)
-   Lombok
-   JUnit 5
-   Allure
-   Selenium
-   Rest-Assured
-   Open API
-   Swagger / Swagger-coverage
-   Docker / Docker Compose 
-   Kafka
-   PostgreSQL
-   JDBI
-   Awaitility

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
2. Navigate to the `RestApiTests` folder of the project using `cd`
3. Run the command: `allure open allure-report`

### How to view Swagger coverage reports:

1. Navigate to the `RestApiTests` folder
2. Open the `swagger-coverage-report` folder
3. Double-click the `swagger-coverage-admin-report.html` file or the `swagger-coverage-frontend-report.html` file

---

## 4. Market Processing Services Integration Tests

This is the implementation of integration test scripts for a market processing application

### Project Structure

-   `MarketDataRecord` — this class is a model for objects of the type `MarketData`, it contains fields from the table `market_data` and getters and setters
-   `MarketProcessingIntegrationTests` — this class contains test scripts for a market processing application

Key features that have been tested:

-   processing valid MarketEvent input
-   processing valid MarketReport input
-   processing invalid input

### Installation

1. Clone this repository (or download ZIP)
2. Open the project in IntelliJ IDEA
3. Open the `DataProcServiceTests` module

### How to run tests

0. Run Docker Desktop
1. Select the `SimpleFinanceTracker\DataProcServiceTests` module using `CMD` or `Terminal` in IntelliJ IDEA
2. Enter the command `docker-compose up -d`
3. Check the launch with the command `docker ps`
4. In IntelliJ IDEA open the Gradle window and select `DataProcServiceTests`
5. Select the task `Task\verification\test` and double-click on it

If database connections are encountered, try the following:

1. Interrupt the contenter's work with the command `docker-compose down -v`
2. Check if the port for PostgreSQL is busy with the command `netstat -aon | findstr :5432`. If the port is busy, should to disable these processes
3. In the file `MarketProcessingIntegrationTests.java` replace the value of variable `POSTGRES_USER` with `"user"` and the value of variable `POSTGRES_PASSWORD` with `"password"`. In the file `docker-compose.yml` replace the value of variables `POSTGRES_USER` and `SPRING_DATASOURCE_USERNAME` with `user` and the value of variables `POSTGRES_PASSWORD` and `SPRING_DATASOURCE_PASSWORD` with `password`
4. Try running the tests again