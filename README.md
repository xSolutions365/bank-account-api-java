# Bank Account Solution

This solution contains a simple Bank Account web application with a REST API, a front-end built with Thymeleaf, and accompanying tests.

## Project Overview

### Bank Account API
- **Controllers**: Contains the `BankAccountController` which handles HTTP requests related to bank accounts.
- **Models**: Defines the `BankAccount` class representing a bank account with properties like `Id`, `AccountNumber`, `AccountHolderName`, and `Balance`.
- **Services**: Implements the `BankAccountService` class that provides business logic for managing bank accounts.

### Bank Account API Tests
- **Controllers**: Contains unit tests for the `BankAccountController` to ensure correct handling of HTTP requests.
- **Services**: Contains unit tests for the `BankAccountService` to verify business logic and data manipulation.
- **End-to-End Tests**: Contains end-to-end tests to verify the complete workflow of the back-end application.

### Bank Account UI (Front-End)
- **Thymeleaf Templates**: Implements a simple front-end for viewing bank accounts.
- **Integration**: Fetches data from the BankAccountAPI using REST calls.
- **Routing**: Ensures /bankaccounts displays the list of bank accounts.

### Bank Account UI Tests
- **Selenium Tests**: Uses Selenium and ChromeDriver to verify UI functionality.

## Setup Instructions

1. Clone the repository:
   ```sh
   git clone <repository-url>
   ```

### Running the API

1. Navigate to the API project directory:
   ```sh
   cd bank-account-api
   ```

2. Build the project:
   ```sh
   ./gradlew build
   ```

3. Run the API:
   ```sh
   ./gradlew bootRun
   ```

4. Open the browser and navigate to:
   ```
   http://localhost:9090/api/BankAccount
   ```
   This will display the list of bank accounts.

5. Run the API tests:
   ```sh
   ./gradlew test
   ```

### Running the UI
Ensure the API is running.

1. Open a new terminal and navigate to the UI project directory:
   ```sh
   cd bank-account-ui

2. Build the UI project:
   ```sh
   ./gradlew build
   ```

3. Run the UI:
   ```sh
   ./gradlew bootRun
   ```

4. Open the browser and navigate to:
   ```
   http://localhost:8080/bankaccounts
   ```
   This will display the Bank Account UI.
   ![Bank Account UI](images/bank-account-ui.png)

5. Run the UI tests (ensure both API and UI are running):
   ```sh
   ./gradlew test
   ```

## Tests

To run all the tests in this project, follow these steps:

1. Ensure you have the necessary dependencies installed and both API and UI projects are running.
2. Open a terminal and navigate to the root directory.
3. Run the following command to execute all tests (API + UI):

   ```sh
   ./gradlew test
   ```
## Context 7 Command
code --add-mcp '{"name":"context7","type":"stdio","command":"npx","args":["@upstash/context7-mcp@1.0.31"],"env":{"CONTEXT7_API_KEY":"${input:CONTEXT7_API_KEY}"},"gallery":"https://api.mcp.github.com","version":"1.0.31"}' --folder-uri .

## Recursive Self-Improvement Prompting
Create a statistical analysis api for the bank. Follow this process:
Generate an initial version of create a statistical analysis api for the bank
Critically evaluate your own output, identifying at least 3 specific weaknesses
Create an improved version addressing those weaknesses
Repeat steps 2-3 two more times, with each iteration focusing on different aspects for improvement
Present your final, most refined version as well as a journal of output from each stage

## Context Aware Decomposition
The bank would like to now offer loans to account holders.  These are separate accounts from savings and the bank will charge a configurable interest rate per loan
Please help me by:
Identifying the core components of this problem (minimum 3, maximum 5)
For each component:
Explain why it's important to the overall problem.
Identify what information or approach is needed to address it
Solve that specific component
After addressing each component separately, synthesize these partial solutions, explicitly addressing how they interact
Provide a holistic solution that maintains awareness of all the components and their relationships
Throughout this process, maintain a "thinking journal" that explains your reasoning at each step.

## Chain of Thought Prompting
Q: The bank pays 2% interest on balances over $1000, but only 1% on balances from $0-1000.  How much interest is paid on a balance of $1500
A: The bank pays $20 interest
Q: The bank will only pay interest on a single account - the account with the largest balance.  If John has one account with a balance of $1000 and another of $500, how much interest will John earn.
A:  John will earn $10 interest from the account with $1000 and $0 interest from the account with $500

## Spec-kit Commands
- /speckit.constitution [prompt] : Generates or updates the constitution.md file. It establishes the "governing principles" of the project, 
- /speckit.specify [prompt] : Generates the Functional Specification. It takes a user's high-level intent and converts it into detailed user stories and requirements.
- /speckit.clarify [prompt] : Identify and resolve ambiguities in the spec (optional)
- /speckit.plan [prompt] : Creates a Technical Implementation Plan. It reads the spec and constitution to set technology stack and architecture.
- /speckit.tasks [prompt] : Converts the technical plan into a checklist of actionable coding tasks.
- /speckit.analyze [prompt] : Validate your plan (optional)
- /speckit.implement [prompt] : Execute the tasks and produce the feature code

## Dependencies

This project requires the following dependencies for testing:

- `JUnit 5`: A popular testing framework for Java.
- `Mockito`: A library for creating mock objects in unit tests.
- `RestAssured`: A library for testing REST APIs.
- `Selenium`: A browser automation framework for UI testing.

## Technologies Used
- Java 21
- Spring Boot
- JUnit 5 (for testing)
- Mockito (for mocking dependencies in tests)
- RestAssured (for API testing)
- Selenium WebDriver (for UI testing)

## Contributing
Feel free to submit issues or pull requests for improvements or bug fixes.
