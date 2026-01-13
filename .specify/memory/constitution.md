# Banking App Constitution

## Purpose & Scope
- **Goal:** Provide a robust, secure, and user-friendly banking system to manage customer bank accounts, balances, and transactions via a Java API and web-based UI.
- **Stakeholders:** End-users (account holders), customer support, administrators, developers/testers.
- **Constraints:**
  - No external database required by default; in-memory storage acceptable for development.
  - Maintain feature parity with .NET and Node.js implementations.
  - Ensure compliance with PCI DSS for secure handling of financial data.
  - Performance constraints: API latency < 200ms for 95% of requests under light load.

## Core Functional Requirements
- **Accounts:**
  - Create, read, update, and delete bank accounts with fields: `Id`, `AccountNumber`, `AccountHolderName`, `Balance`.
  - Ensure unique account numbers and enforce validation rules.
- **Transactions:**
  - Deposit and withdraw funds with validation (e.g., no negative amounts, sufficient balance).
  - Transfer funds between accounts atomically (debit source, credit destination, rollback on failure).
- **Statements:**
  - Retrieve transaction history per account with timestamps, amounts, types, and running balances.
- **Authentication & Authorization:**
  - Implement role-based access control: `User`, `Support`, `Admin` roles.
  - Support OAuth/OIDC for production environments.
- **Audit Logging:**
  - Record CRUD and financial operations with details (who, what, when, before/after balances).
- **Error Handling:**
  - Provide consistent API error responses with codes, messages, and correlation IDs.

## API Contracts
- **Accounts Endpoints:**
  - `GET /api/BankAccount`: List accounts with pagination and search.
  - `GET /api/BankAccount/{id}`: Retrieve a single account by ID.
  - `POST /api/BankAccount`: Create a new account. Body: `{ accountNumber, accountHolderName, balance }`.
  - `PUT /api/BankAccount/{id}`: Update account details.
  - `DELETE /api/BankAccount/{id}`: Delete an account.
- **Transactions Endpoints:**
  - `POST /api/BankAccount/{id}/deposit`: Deposit funds. Body: `{ amount }`.
  - `POST /api/BankAccount/{id}/withdraw`: Withdraw funds. Body: `{ amount }`.
  - `POST /api/BankAccount/transfer`: Transfer funds. Body: `{ sourceAccountId, destinationAccountId, amount }`.
  - `GET /api/BankAccount/{id}/statements`: Retrieve transaction history.
- **Error Responses:**
  - Standardized format: `{ error: { code, message }, correlationId }`.
  - HTTP status codes: 400 (validation error), 404 (not found), 409 (conflict), 422 (business rule violation), 500 (server error).

## Validation Rules
- **Account Validation:**
  - `AccountNumber`: Required, unique, format `^[0-9]{8,12}$`.
  - `AccountHolderName`: Required, min length 2, max length 100.
  - `Balance`: Must be >= 0 on creation; decimal precision to 2 places.
- **Transaction Validation:**
  - `Amount`: Must be > 0; decimal precision to 2 places.
  - Withdrawals: Cannot result in a negative balance.
  - Transfers: Validate both accounts exist; insufficient funds → 422; same-account transfers → 400.

## UI Requirements
- **Pages:**
  - `Index`: Welcome page with links to account management.
  - `BankAccounts`: List accounts with search and pagination; button to create new accounts.
  - `CreateBankAccount`: Form for creating accounts with inline validation.
  - `AccountDetails`: Display account balance, recent transactions, and actions (deposit/withdraw).
- **Behavior:**
  - Inline validation for forms.
  - Friendly error messages and loading states.
  - Empty-state messaging for lists.
- **Styling:**
  - Responsive design with CSS.
  - Accessibility features: ARIA labels, keyboard navigation, and color contrast compliance.

## Testing Strategy
- **Unit Tests:**
  - Cover models, validation logic, and service methods (e.g., account CRUD, transactions).
- **Integration Tests:**
  - Test controller endpoints with in-memory storage.
  - Verify error handling and edge cases.
- **End-to-End Tests:**
  - Use Selenium or equivalent for UI workflows (e.g., account creation, transactions).
- **Performance Tests:**
  - Measure API latency and error rates under load.
- **Security Tests:**
  - Fuzz inputs, validate authentication/authorization, and test CSRF protection.

## Roadmap
- **Phase 1:** Implement CRUD operations, search, and validation.
- **Phase 2:** Add deposit, withdrawal, and statement features.
- **Phase 3:** Implement fund transfers and audit logging.
- **Phase 4:** Introduce authentication and role-based access control.
- **Phase 5:** Enhance observability and integrate persistent storage.

## Governance
- The constitution supersedes all other practices and must be adhered to by all contributors.
- Amendments require documentation, approval, and a migration plan.
- All pull requests and reviews must verify compliance with the constitution.

**Version**: 1.0.1 | **Ratified**: 2025-12-18 | **Last Amended**: 2025-12-18
