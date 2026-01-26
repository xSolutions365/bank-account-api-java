# Tasks: Total Balance API

**Feature**: Total Balance API  
**Branch**: 001-total-balance  
**Input**: Design documents from `/specs/001-total-balance/`  
**Prerequisites**: ✅ plan.md, ✅ spec.md, ✅ research.md, ✅ data-model.md, ✅ contracts/total-balance-api.yaml

**Tests**: Tests ARE REQUIRED per constitution (Principle II - Comprehensive Testing NON-NEGOTIABLE)

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `- [ ] [ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Path Conventions

This is a web application with backend Python API. All paths are under `bank-account-api-py/`.

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Verify development environment and existing infrastructure

**⚠️ Note**: No setup tasks needed - all infrastructure already exists. This feature extends existing code.

**Status**: ✅ Complete - verified during planning phase

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Verify foundational infrastructure that MUST be complete before user story implementation

**⚠️ CRITICAL**: These tasks validate existing infrastructure. No user story work can begin until verified.

- [X] T001 Verify pytest test framework is working in bank-account-api-py/tests/
- [X] T002 Verify BankAccountService class exists and has get_all_accounts() method in bank-account-api-py/app/bank_account/service.py
- [X] T003 [P] Verify FastAPI router is configured in bank-account-api-py/app/bank_account/controller.py

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - Retrieve Total Balance (Priority: P1) 🎯 MVP

**Goal**: Implement GET /api/BankAccount/total endpoint that returns the sum of all account balances

**Independent Test**: Call the endpoint and verify returned sum matches total of all account balances in the system

### Tests for User Story 1 ⚠️ WRITE TESTS FIRST (TDD)

> **CRITICAL: Write these tests FIRST, ensure they FAIL before implementation**

- [X] T004 [P] [US1] Write unit test for get_total_balance() with empty account list in bank-account-api-py/tests/unit/test_bank_account_service.py
- [X] T005 [P] [US1] Write unit test for get_total_balance() with positive balances in bank-account-api-py/tests/unit/test_bank_account_service.py
- [X] T006 [P] [US1] Write unit test for get_total_balance() with negative balances in bank-account-api-py/tests/unit/test_bank_account_service.py
- [X] T007 [P] [US1] Write unit test for get_total_balance() with mixed positive and negative balances in bank-account-api-py/tests/unit/test_bank_account_service.py
- [X] T008 [P] [US1] Write integration test for GET /api/BankAccount/total with no accounts in bank-account-api-py/tests/e2e/test_bank_account_e2e.py
- [X] T009 [P] [US1] Write integration test for GET /api/BankAccount/total with multiple accounts in bank-account-api-py/tests/e2e/test_bank_account_e2e.py

**Checkpoint**: All tests written and FAILING (as expected per TDD)

### Implementation for User Story 1

- [X] T010 [US1] Implement get_total_balance() class method in bank-account-api-py/app/bank_account/service.py
- [X] T011 [US1] Add GET /api/BankAccount/total endpoint in bank-account-api-py/app/bank_account/controller.py
- [X] T012 [US1] Add type hints for get_total_balance() return type and response model in bank-account-api-py/app/bank_account/service.py
- [X] T013 [US1] Run all US1 tests and verify they pass
- [X] T014 [US1] Run code quality checks (black, flake8, mypy) and fix any issues

**Checkpoint**: User Story 1 is fully functional and testable independently - MVP COMPLETE ✅

---

## Phase 4: User Story 2 - Currency Formatting (Priority: P2)

**Goal**: Ensure total balance is returned with proper currency formatting (2 decimal places, currency code)

**Independent Test**: Call endpoint and verify response includes properly formatted values with currency code

### Tests for User Story 2 ⚠️ WRITE TESTS FIRST (TDD)

- [X] T015 [P] [US2] Write integration test verifying response includes currency field set to "USD" in bank-account-api-py/tests/e2e/test_bank_account_e2e.py
- [X] T016 [P] [US2] Write integration test verifying balance has exactly 2 decimal places in bank-account-api-py/tests/e2e/test_bank_account_e2e.py
- [X] T017 [P] [US2] Write integration test verifying large balance values maintain precision in bank-account-api-py/tests/e2e/test_bank_account_e2e.py

**Checkpoint**: All US2 tests written and FAILING

### Implementation for User Story 2

- [X] T018 [US2] Update controller response to include currency field ("USD") in bank-account-api-py/app/bank_account/controller.py
- [X] T019 [US2] Verify JSON serialization maintains 2 decimal precision (default FastAPI behavior)
- [X] T020 [US2] Run all US2 tests and verify they pass
- [X] T021 [US2] Run code quality checks and fix any issues

**Checkpoint**: User Stories 1 AND 2 are both working independently

---

## Phase 5: User Story 3 - Include Account Count (Priority: P3)

**Goal**: Add account_count field to response for additional context

**Independent Test**: Call endpoint and verify response includes count field matching total number of accounts

### Tests for User Story 3 ⚠️ WRITE TESTS FIRST (TDD)

- [X] T022 [P] [US3] Write unit test for get_account_count() method in bank-account-api-py/tests/unit/test_bank_account_service.py
- [X] T023 [P] [US3] Write integration test verifying response includes account_count field in bank-account-api-py/tests/e2e/test_bank_account_e2e.py
- [X] T024 [P] [US3] Write integration test verifying account_count is 0 when no accounts exist in bank-account-api-py/tests/e2e/test_bank_account_e2e.py

**Checkpoint**: All US3 tests written and FAILING

### Implementation for User Story 3

- [X] T025 [US3] Add get_account_count() class method to BankAccountService in bank-account-api-py/app/bank_account/service.py
- [X] T026 [US3] Update controller to include account_count in response in bank-account-api-py/app/bank_account/controller.py
- [X] T027 [US3] Run all US3 tests and verify they pass
- [X] T028 [US3] Run code quality checks and fix any issues

**Checkpoint**: All user stories are now independently functional

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final quality improvements and documentation updates

- [X] T029 [P] Run full test suite with coverage report (target: 80%+ coverage) - Achieved 89%
- [X] T030 [P] Verify all code quality tools pass (black, flake8, mypy) with no warnings
- [X] T031 [P] Update API documentation if needed (FastAPI auto-generates from code)
- [X] T032 [P] Test performance with large dataset (simulate 10,000 accounts, verify < 2 second response)
- [X] T033 Verify quickstart.md examples work against implemented endpoint
- [X] T034 Run end-to-end smoke test of entire feature
- [X] T035 [P] Update README.md if new setup steps required (none expected)

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - verification only ✅
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3-5)**: All depend on Foundational phase completion
  - User stories can then proceed in parallel (if staffed)
  - Or sequentially in priority order (P1 → P2 → P3)
- **Polish (Phase 6)**: Depends on all desired user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Can start after Foundational (Phase 2) - No dependencies on other stories
- **User Story 2 (P2)**: Depends on User Story 1 completion (extends US1 response format)
- **User Story 3 (P3)**: Depends on User Story 1 completion (extends US1 response with additional field)

### Within Each User Story

**Critical TDD Flow**:
1. Tests MUST be written first and MUST fail
2. Implementation follows to make tests pass
3. Code quality checks run after tests pass
4. Story complete before moving to next priority

### Parallel Opportunities

**Phase 2 (Foundational)**:
- All 3 verification tasks (T001, T002, T003) can run in parallel

**Phase 3 (User Story 1) - Test Writing**:
- All unit tests (T004-T007) can be written in parallel
- All integration tests (T008-T009) can be written in parallel

**Phase 4 (User Story 2) - Test Writing**:
- All US2 tests (T015-T017) can be written in parallel

**Phase 5 (User Story 3) - Test Writing**:
- All US3 tests (T022-T024) can be written in parallel

**Phase 6 (Polish)**:
- Most polish tasks (T029-T032, T035) can run in parallel

**Sequential Requirements**:
- Tests must complete before implementation (TDD)
- Implementation must complete before quality checks
- US1 must complete before US2 or US3 can start

---

## Parallel Example: User Story 1

**Scenario**: Team of 2 developers implementing US1

```bash
# Developer 1: Write unit tests in parallel
# Terminal 1
cd bank-account-api-py
# Write T004, T005, T006, T007 simultaneously

# Developer 2: Write integration tests in parallel  
# Terminal 2
cd bank-account-api-py
# Write T008, T009 simultaneously

# After tests complete, verify they fail
pytest tests/unit/test_bank_account_service.py::test_get_total_balance_empty
pytest tests/e2e/test_bank_account_e2e.py::test_total_balance_endpoint

# Developer 1: Implement service layer
# Work on T010, T012

# Developer 2: Implement controller layer
# Work on T011 (waits for T010 to complete)

# Both: Run tests and quality checks
# T013, T014
```

**Estimated Time for US1**: 1.5-2 hours (following strict TDD)

---

## Implementation Strategy

### MVP First (User Story 1 Only)

**Recommended approach**: Implement US1 completely before starting US2 or US3

**Benefits**:
- Delivers working feature quickly
- Validates architecture and patterns
- Can be deployed independently
- Reduces integration risk

**Timeline**:
- US1: 2 hours (core functionality)
- US2: 30 minutes (add currency field)
- US3: 45 minutes (add count field)
- Polish: 1 hour (testing, quality, documentation)
- **Total**: ~4.25 hours for complete feature

### Incremental Delivery

Each user story can be deployed independently:
1. Deploy US1 → basic total balance available
2. Deploy US2 → adds currency formatting
3. Deploy US3 → adds account count context

### Risk Mitigation

**Key Risks Addressed**:
- TDD approach catches bugs early
- Independent stories reduce integration complexity
- Parallel test writing speeds up development
- Quality checks enforced at each story completion

---

## Task Summary

| Phase | Tasks | Parallelizable | Sequential | Estimated Time |
|-------|-------|----------------|------------|----------------|
| Phase 1: Setup | 0 | 0 | 0 | 0 min (already complete) |
| Phase 2: Foundational | 3 | 3 | 0 | 15 min |
| Phase 3: US1 | 11 | 6 | 5 | 2 hours |
| Phase 4: US2 | 7 | 3 | 4 | 30 min |
| Phase 5: US3 | 7 | 3 | 4 | 45 min |
| Phase 6: Polish | 7 | 5 | 2 | 1 hour |
| **TOTAL** | **35 tasks** | **20** | **15** | **~4.5 hours** |

---

## Ready to Start Implementation

**Next Steps**:
1. Start with Phase 2 verification tasks (T001-T003)
2. Move to Phase 3, beginning with writing failing tests (T004-T009)
3. Implement US1 service and controller (T010-T011)
4. Verify US1 tests pass (T013)
5. Continue with US2 and US3 as needed

**Constitution Compliance**: ✅ All tasks follow TDD discipline and MVC separation

**MVP Milestone**: Complete through T014 (User Story 1) for minimum viable product

---

**Tasks Generated**: 2026-01-14  
**Ready for Implementation**: ✅ YES  
**Test-Driven**: ✅ YES (TDD enforced)