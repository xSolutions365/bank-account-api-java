# Bank Account Solution

This solution contains a simple Bank Account MVC project with a REST API and accompanying unit tests.

## Project Overview

### Bank Account API
- **Controllers**: Contains the `BankAccountController` which handles HTTP requests related to bank accounts.
- **Models**: Defines the `BankAccount` class representing a bank account with properties like `Id`, `AccountNumber`, `AccountHolderName`, and `Balance`.
- **Services**: Implements the `BankAccountService` class that provides business logic for managing bank accounts.

### Bank Account API Tests
- **Controllers**: Contains unit tests for the `BankAccountController` to ensure correct handling of HTTP requests.
- **Services**: Contains unit tests for the `BankAccountService` to verify business logic and data manipulation.
- **End-to-End Tests**: Contains end-to-end tests to verify the complete workflow of the back-end application.

## Setup Instructions

1. Clone the repository:
   ```sh
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```sh
   cd bank-account-api
   ```

3. Build the project:
   ```sh
   ./gradlew build
   ```

4. Run the API:
   ```sh
   ./gradlew bootRun
   ```

5. Open the browser and navigate to:
   ```
   http://localhost:9090/api/BankAccount
   ```
   This will display the list of bank accounts.

6. Run the tests:
   ```sh
   ./gradlew test
   ```

## Running Tests

To run the tests in this project, follow these steps:

1. Ensure you have the necessary dependencies installed.
2. Open a terminal and navigate to the project directory.
3. Run the following command to execute the tests:
   ```sh
   ./gradlew test
   ```

## Dependencies

This project requires the following dependencies for testing:

- `JUnit 5`: A popular testing framework for Java.
- `Mockito`: A library for creating mock objects in unit tests.
- `RestAssured`: A library for testing REST APIs.

## Technologies Used
- Java 21
- Spring Boot
- JUnit 5 (for testing)
- Mockito (for mocking dependencies in tests)
- RestAssured (for API testing)

## Contributing
Feel free to submit issues or pull requests for improvements or bug fixes.
