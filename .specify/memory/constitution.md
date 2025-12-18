# Banking App Constitution

## Purpose & Scope
- **Goal:** Provide a simple, secure banking system to manage customer bank accounts, balances, and transactions via a .NET 8 API and Razor Pages UI.
- **Stakeholders:** End-users (account holders), customer support, administrators, developers/testers.
- **Constraints:** No external DB required by default (in-memory acceptable for dev). Keep parity with Java and Node variants when possible.

## Core Functional Requirements
- **Accounts:** Create, read, update, delete bank accounts with fields: `Id`, `AccountNumber`, `AccountHolderName`, `Balance`.
- **Search:** Search accounts by holder name (supports partial matches), and by account number (exact match).
- **Transactions:** Deposit and withdraw funds with validation (no negative amounts, sufficient balance).
- **Transfers:** Transfer between accounts (atomic: debit source, credit destination, rollback on failure).
- **Statements:** Retrieve transaction history per account with timestamps, amounts, types, running balance.
- **Validation:** Server-side and client-side validation for required fields, formats, and business rules.
- **Authentication:** Basic auth support (pluggable). For demo: optional simple auth; for prod: OAuth/OIDC.
- **Authorization:** Roles: `User` (own accounts), `Support` (read-only across accounts), `Admin` (CRUD, config).
- **Audit Logging:** Record CRUD and financial operations (who, what, when, before/after balances).
- **Error Handling:** Consistent API error schema with codes, messages, and correlation IDs.

## Non-Functional Requirements
- **Security:**
	- Validate all inputs; prevent injection and overflows.
	- Enforce HTTPS; secure headers; CSRF protection for UI forms.
	- Rate limiting on sensitive endpoints; lockout on repeated auth failures.
	- Secrets via environment or KeyVault-equivalent; no secrets in repo.
- **Compliance:** Provide hooks for KYC/AML checks; maintain audit trail; configurable data retention.
- **Performance:** P95 API latency < 200ms under light load; support pagination for lists.
- **Availability:** Graceful startup/shutdown; health endpoints (`/health`, `/ready`).
- **Scalability:** Stateless API; storage abstraction to allow DB swap (e.g., EF Core later).
- **Observability:** Structured logs, request IDs, minimal metrics (requests, errors, latency), trace hooks.
- **Accessibility:** UI meets WCAG AA basics: keyboard navigation, ARIA labels, color contrast.

## API Contracts (High-Level)
- **Accounts**
	- `GET /api/BankAccount`: List accounts (supports `page`, `pageSize`, `search`).
	- `GET /api/BankAccount/{id}`: Get single account.
	- `GET /api/BankAccount/search?name=...`: Partial name search.
	- `POST /api/BankAccount`: Create account. Body: `{ accountNumber, accountHolderName, balance }`.
	- `PUT /api/BankAccount/{id}`: Update account holder name or account number.
	- `DELETE /api/BankAccount/{id}`: Delete account (fails if negative side effects).
- **Transactions**
	- `POST /api/BankAccount/{id}/deposit`: `{ amount }`.
	- `POST /api/BankAccount/{id}/withdraw`: `{ amount }`.
	- `POST /api/BankAccount/transfer`: `{ sourceAccountId, destinationAccountId, amount }`.
	- `GET /api/BankAccount/{id}/statements`: List transactions.
- **Errors**
	- Response: `{ error: { code, message }, correlationId }`. Use HTTP status codes: 400 validation, 404 not found, 409 conflict, 422 business rule, 500 unexpected.

## Validation Rules
- **Account:**
	- `AccountNumber`: required, unique, format `^[0-9]{8,12}$`.
	- `AccountHolderName`: required, min length 2, max 100.
	- `Balance`: >= 0 on creation; decimal precision to 2 places.
- **Transactions:**
	- `amount` > 0 and precision 2.
	- Withdraw cannot result in negative balance.
	- Transfer must validate both accounts exist; insufficient funds -> 422; same-account transfers -> 400.

## UI Requirements (Razor Pages)
- **Pages:**
	- `Index`: welcome/hero; link to accounts.
	- `BankAccounts`: list with search and pagination; button to create.
	- `CreateBankAccount`: form with validation feedback; success redirect.
	- `AccountDetails`: show balance, recent transactions, deposit/withdraw actions.
- **Behavior:** Inline validation, friendly errors, loading states, empty-state messaging.
- **Styling:** Use `wwwroot/css/bank.css`; responsive layout; hero image partial `_BankHeroImage.cshtml`.

## Testing Strategy
- **Unit Tests:** Models, validation, services (account CRUD, search, deposit/withdraw, transfer).
- **Integration Tests:** Controller endpoints with in-memory store; verify error responses and edge cases.
- **E2E UI Tests:** Selenium for list, create, deposit/withdraw, and error scenarios.
- **Performance Tests:** Basic load for list/search; measure latency and error rates.
- **Security Tests:** Input fuzzing; auth/role checks; CSRF coverage for forms.

## Architecture & Implementation Notes
- **Service Layer:** `IBankAccountService` encapsulates business logic for accounts, transactions, transfers, and statements.
- **Controller Layer:** `BankAccountController` handles REST endpoints, validation mapping, and error semantics.
- **Data Layer:** Initially in-memory; design `IAccountRepository` (optional) for future persistence.
- **Configuration:** `appsettings.json` for UI/API endpoints; secrets externalized.
- **Cross-Stack Parity:** Reflect features in Java and Node stacks when feasible.

## Roadmap (Iterative)
- Phase 1: CRUD, search, validation (done/in-progress).
- Phase 2: Transactions (deposit, withdraw) and statements.
- Phase 3: Transfers and audit logging.
- Phase 4: AuthZ/AuthN and role-based access.
- Phase 5: Persistence (EF Core) and observability hardening.

## Glossary
- **Account:** Entity representing a customer’s bank account and balance.
- **Transaction:** A financial operation affecting balance (deposit, withdraw, transfer).
- **Statement:** Ordered list of transactions with running totals.

# [PROJECT_NAME] Constitution
<!-- Example: Spec Constitution, TaskFlow Constitution, etc. -->

## Core Principles

### [PRINCIPLE_1_NAME]
<!-- Example: I. Library-First -->
[PRINCIPLE_1_DESCRIPTION]
<!-- Example: Every feature starts as a standalone library; Libraries must be self-contained, independently testable, documented; Clear purpose required - no organizational-only libraries -->

### [PRINCIPLE_2_NAME]
<!-- Example: II. CLI Interface -->
[PRINCIPLE_2_DESCRIPTION]
<!-- Example: Every library exposes functionality via CLI; Text in/out protocol: stdin/args → stdout, errors → stderr; Support JSON + human-readable formats -->

### [PRINCIPLE_3_NAME]
<!-- Example: III. Test-First (NON-NEGOTIABLE) -->
[PRINCIPLE_3_DESCRIPTION]
<!-- Example: TDD mandatory: Tests written → User approved → Tests fail → Then implement; Red-Green-Refactor cycle strictly enforced -->

### [PRINCIPLE_4_NAME]
<!-- Example: IV. Integration Testing -->
[PRINCIPLE_4_DESCRIPTION]
<!-- Example: Focus areas requiring integration tests: New library contract tests, Contract changes, Inter-service communication, Shared schemas -->

### [PRINCIPLE_5_NAME]
<!-- Example: V. Observability, VI. Versioning & Breaking Changes, VII. Simplicity -->
[PRINCIPLE_5_DESCRIPTION]
<!-- Example: Text I/O ensures debuggability; Structured logging required; Or: MAJOR.MINOR.BUILD format; Or: Start simple, YAGNI principles -->

## [SECTION_2_NAME]
<!-- Example: Additional Constraints, Security Requirements, Performance Standards, etc. -->

[SECTION_2_CONTENT]
<!-- Example: Technology stack requirements, compliance standards, deployment policies, etc. -->

## [SECTION_3_NAME]
<!-- Example: Development Workflow, Review Process, Quality Gates, etc. -->

[SECTION_3_CONTENT]
<!-- Example: Code review requirements, testing gates, deployment approval process, etc. -->

## Governance
<!-- Example: Constitution supersedes all other practices; Amendments require documentation, approval, migration plan -->

[GOVERNANCE_RULES]
<!-- Example: All PRs/reviews must verify compliance; Complexity must be justified; Use [GUIDANCE_FILE] for runtime development guidance -->

**Version**: [CONSTITUTION_VERSION] | **Ratified**: [RATIFICATION_DATE] | **Last Amended**: [LAST_AMENDED_DATE]
<!-- Example: Version: 2.1.1 | Ratified: 2025-06-13 | Last Amended: 2025-07-16 -->
