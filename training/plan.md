# Implementation Plan: Total Balance API

**Branch**: `001-total-balance` | **Date**: 2026-01-14 | **Spec**: [spec.md](spec.md)
**Input**: Feature specification from `/specs/001-total-balance/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

Create a REST API endpoint that calculates and returns the total balance across all bank accounts. The implementation will extend the existing bank account service with a new endpoint (`GET /api/BankAccount/total`) that sums all account balances and returns the result in a consistent JSON format. The solution will follow the established MVC pattern with comprehensive test coverage including unit tests for the service logic and integration tests for the complete request/response cycle.

## Technical Context

**Language/Version**: Python 3.9+  
**Primary Dependencies**: FastAPI 0.115.0, Uvicorn 0.32.0  
**Storage**: In-memory (existing `BankAccountService._accounts` list)  
**Testing**: pytest 8.3.0, pytest-cov 6.0.0, pytest-asyncio 0.24.0, httpx 0.27.0  
**Target Platform**: Linux/macOS server (development), containerized deployment (production)  
**Project Type**: Web application (backend API only for this feature)  
**Performance Goals**: < 2 seconds response time for 10,000 accounts, handle 100 concurrent requests  
**Constraints**: < 3 seconds response time under normal load, maintain 2 decimal precision, no rounding errors  
**Scale/Scope**: Single endpoint addition, ~150 lines of code (service + controller + tests), extends existing bank account module

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### ✅ I. Separation of Concerns (MVC Architecture)
- **Status**: PASS
- **Rationale**: Feature extends existing MVC structure in `bank-account-api-py/app/bank_account/`
- **Implementation**: 
  - Model: Reuses existing `BankAccount` model (no changes needed)
  - Service: Add `get_total_balance()` method to existing `BankAccountService`
  - Controller: Add new `GET /api/BankAccount/total` endpoint to existing router
  - Clear separation maintained with no cross-layer dependencies

### ✅ II. Comprehensive Testing (NON-NEGOTIABLE)
- **Status**: PASS
- **Rationale**: Full test coverage planned following test-first approach
- **Implementation**:
  - Unit tests: Test service method with various scenarios (empty, positive, negative, mixed balances)
  - Integration tests: Test complete endpoint with request/response cycle
  - Target coverage: 100% for new service method, full endpoint coverage
  - Tests written before implementation per TDD discipline

### ✅ III. Code Quality Standards
- **Status**: PASS
- **Rationale**: All existing quality tools apply automatically
- **Implementation**:
  - black formatting (100 char line length)
  - flake8 linting
  - mypy type checking with full type hints
  - All quality gates must pass before merge

### ✅ IV. API-First Design
- **Status**: PASS
- **Rationale**: REST endpoint follows existing API patterns
- **Implementation**:
  - Endpoint: `GET /api/BankAccount/total`
  - Returns JSON: `{"total_balance": float, "currency": "USD"}`
  - Consistent with existing `/api/BankAccount` namespace
  - No breaking changes to existing API

### ✅ V. Dependency Management and Reproducibility
- **Status**: PASS
- **Rationale**: No new dependencies required
- **Implementation**:
  - Uses existing FastAPI, pytest stack
  - No changes to `pyproject.toml` or `requirements.txt`
  - Fully reproducible with existing environment

### 🎯 Overall Constitution Compliance: PASS

**No violations or deviations.** Feature aligns perfectly with all constitutional principles and requires no complexity justification.

## Project Structure

### Documentation (this feature)

```text
specs/001-total-balance/
├── plan.md              # This file (implementation plan)
├── spec.md              # Feature specification (complete)
├── research.md          # Phase 0 output (no research needed - straightforward implementation)
├── data-model.md        # Phase 1 output (data contracts)
├── quickstart.md        # Phase 1 output (API usage guide)
├── contracts/           # Phase 1 output (API contract)
│   └── total-balance-api.yaml
└── checklists/
    └── requirements.md  # Spec quality checklist (complete)
```

### Source Code (repository root)

```text
bank-account-api-py/           # Backend Python application
├── app/
│   ├── main.py                # FastAPI app (no changes)
│   └── bank_account/          # Bank account feature module
│       ├── __init__.py        # Module init (no changes)
│       ├── model.py           # BankAccount model (no changes)
│       ├── service.py         # ✨ ADD: get_total_balance() method
│       └── controller.py      # ✨ ADD: GET /api/BankAccount/total endpoint
├── tests/
│   ├── unit/
│   │   ├── test_bank_account_service.py  # ✨ ADD: test_get_total_balance tests
│   │   ├── test_bank_account_model.py    # (no changes)
│   │   └── test_bank_account_controller.py  # (no changes)
│   └── e2e/
│       └── test_bank_account_e2e.py      # ✨ ADD: test_total_balance_endpoint
├── pyproject.toml             # Poetry dependencies (no changes)
└── requirements.txt           # Pip dependencies (no changes)

bank-account-ui/               # Frontend (out of scope for this feature)
└── (no changes)
```

**Structure Decision**: This is a web application with separate backend and frontend. This feature only affects the backend Python application (`bank-account-api-py/`). We extend the existing `bank_account` module following the established MVC pattern:

- **Model**: No changes needed - reuses existing `BankAccount` class
- **Service**: Add one new class method `get_total_balance()` to `BankAccountService`
- **Controller**: Add one new route handler to the existing `router` in `controller.py`
- **Tests**: Add unit tests in existing test file, add integration test in e2e file

The implementation is minimal and surgical, touching only 2 production files and 2 test files.

## Complexity Tracking

> **No complexity tracking required** - Constitution Check shows PASS on all principles with no violations or deviations.

---

## Phase 1 Completion: Post-Design Constitution Re-Check

### ✅ Constitution Re-Validation (After Design)

All constitutional principles remain satisfied after completing the design phase:

**I. Separation of Concerns (MVC)**: ✅ PASS
- Design confirms clean MVC separation
- Service layer: pure business logic (sum calculation)
- Controller layer: HTTP handling only
- No model changes required

**II. Comprehensive Testing**: ✅ PASS
- Test strategy documented in research.md
- Unit test scenarios defined (empty, positive, negative, mixed)
- Integration test approach specified
- E2E test coverage planned

**III. Code Quality Standards**: ✅ PASS
- Type hints documented in data-model.md
- Code will follow existing black/flake8/mypy standards
- No quality exceptions required

**IV. API-First Design**: ✅ PASS
- OpenAPI contract created (contracts/total-balance-api.yaml)
- Endpoint design consistent with existing API
- JSON response format documented
- No breaking changes

**V. Dependency Management**: ✅ PASS
- No new dependencies introduced
- Existing environment sufficient
- Agent context updated with feature details

### 📋 Phase Outputs Checklist

- [x] **plan.md** - Implementation plan (this file)
- [x] **research.md** - Technical research and decisions
- [x] **data-model.md** - Entity and response models
- [x] **contracts/total-balance-api.yaml** - OpenAPI specification
- [x] **quickstart.md** - API usage guide
- [x] **Agent context updated** - GitHub Copilot instructions.md

### 🎯 Phase 1 Summary

**Status**: ✅ COMPLETE - Ready for Phase 2 (Task Breakdown)

**Key Decisions**:
1. Use Python's built-in `sum()` with generator expression
2. Return simple JSON response (no complex DTO classes)
3. No caching or optimization in MVP
4. Reuse existing infrastructure 100%
5. Float precision sufficient (no Decimal type needed)

**Files to Create** (Phase 2 implementation):
- `bank-account-api-py/app/bank_account/service.py` - Add 1 method (~10 lines)
- `bank-account-api-py/app/bank_account/controller.py` - Add 1 endpoint (~8 lines)
- `bank-account-api-py/tests/unit/test_bank_account_service.py` - Add 4-5 test cases (~40 lines)
- `bank-account-api-py/tests/e2e/test_bank_account_e2e.py` - Add 2-3 integration tests (~30 lines)

**Estimated Implementation Time**: 2-3 hours (following TDD)

**Next Command**: `/speckit.tasks` to generate detailed task breakdown

---

**Plan Completed**: 2026-01-14  
**Constitution Compliance**: ✅ FULL COMPLIANCE  
**Ready for Implementation**: ✅ YES
