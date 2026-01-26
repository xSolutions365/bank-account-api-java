# Data Model: Total Balance API

**Feature**: Total Balance API  
**Branch**: 001-total-balance  
**Date**: 2026-01-14

## Overview

This feature introduces one new response entity (`TotalBalanceResponse`) that aggregates data from the existing `BankAccount` entity. No new persistent entities or database schema changes are required.

## Entities

### TotalBalanceResponse (NEW)

**Purpose**: Represents the aggregate balance across all bank accounts

**Type**: Response DTO (Data Transfer Object) - ephemeral, not persisted

**Attributes**:

| Field | Type | Required | Description | Validation |
|-------|------|----------|-------------|------------|
| `total_balance` | float | Yes | Sum of all account balances | >= 0 for display, can be negative mathematically |
| `currency` | string | Yes | Currency code for the balance | Fixed: "USD" |
| `account_count` | integer | No (P3) | Number of accounts in calculation | >= 0 |
| `calculated_at` | string (ISO 8601) | No (P3) | Timestamp of calculation | ISO 8601 format |

**Example JSON**:
```json
{
  "total_balance": 1234567.89,
  "currency": "USD"
}
```

**Example JSON (with optional fields - P3)**:
```json
{
  "total_balance": 1234567.89,
  "currency": "USD",
  "account_count": 42,
  "calculated_at": "2026-01-14T10:30:00Z"
}
```

### BankAccount (EXISTING - No Changes)

**Purpose**: Represents an individual customer bank account

**Relevant Attributes for This Feature**:

| Field | Type | Description |
|-------|------|-------------|
| `id` | int | Unique account identifier |
| `balance` | float | Current account balance (can be positive or negative) |
| `account_number` | string | Account number (not used for total) |
| `account_holder_name` | string | Account holder name (not used for total) |

**Relationship**: `TotalBalanceResponse` is computed from the aggregate of all `BankAccount.balance` values.

## Data Flow

```
┌─────────────────┐
│  BankAccount 1  │
│  balance: 100.0 │
└────────┬────────┘
         │
         ├──────────► ┌─────────────────────┐
┌─────────────────┐  │  BankAccountService │
│  BankAccount 2  │  │  get_total_balance()│
│  balance: 250.0 ├──►│                     │──► TotalBalanceResponse
└─────────────────┘  │  sum(balances)      │    { total_balance: 600.0 }
         │           └─────────────────────┘
         ├──────────►
┌─────────────────┐
│  BankAccount 3  │
│  balance: 250.0 │
└─────────────────┘
```

## Calculations

### Total Balance Calculation

**Formula**: 
```
total_balance = Σ(account.balance for all accounts)
```

**Implementation Notes**:
- Uses Python's built-in `sum()` function
- Includes both positive and negative balances
- Returns 0.0 if no accounts exist
- Precision: 2 decimal places (standard float precision)

**Edge Cases**:

| Scenario | Input | Output | Notes |
|----------|-------|--------|-------|
| No accounts | `[]` | `{"total_balance": 0.0, "currency": "USD"}` | Empty sum returns 0 |
| All positive | `[100, 200, 300]` | `{"total_balance": 600.0, "currency": "USD"}` | Standard case |
| All negative | `[-50, -100]` | `{"total_balance": -150.0, "currency": "USD"}` | Overdrafts included |
| Mixed | `[500, -50, 200]` | `{"total_balance": 650.0, "currency": "USD"}` | Net calculation |
| Large values | `[999999999.99]` | `{"total_balance": 999999999.99, "currency": "USD"}` | No overflow |

### Account Count Calculation (P3 - Optional)

**Formula**:
```
account_count = len(accounts)
```

**Implementation Notes**:
- Simple count of accounts in the list
- Returns 0 if no accounts exist

## Validation Rules

### Input Validation

**None required** - endpoint accepts no parameters

### Output Validation

| Rule | Description | Error Handling |
|------|-------------|----------------|
| Balance type | Must be numeric (float) | Guaranteed by Python type system |
| Precision | 2 decimal places | JSON serialization handles automatically |
| Currency | Must be "USD" | Hardcoded constant |

## Data Constraints

### Scale Limits

| Constraint | Value | Rationale |
|------------|-------|-----------|
| Max balance value | 1 trillion (1,000,000,000,000.00) | Python float precision limit for 2 decimals |
| Max accounts | 10,000 (performance target) | Tested performance requirement |
| Min balance value | -1 trillion | Symmetrical with max value |
| Decimal precision | 2 places | Standard financial precision |

### Performance Characteristics

| Operation | Complexity | Time (10K accounts) | Notes |
|-----------|------------|---------------------|-------|
| Sum calculation | O(n) | ~1ms | Linear scan of accounts |
| JSON serialization | O(1) | ~0.1ms | Fixed-size response |
| Total response time | O(n) | < 2ms | Well under 2-second target |

## Database Schema

**Not Applicable** - This feature uses in-memory data structures only.

The existing `BankAccount` class is stored in `BankAccountService._accounts` (class variable, list type). No database tables or migrations required.

## Type Definitions

### Python Type Hints (Service Layer)

```python
from typing import List, Dict, Any

class BankAccountService:
    _accounts: List[BankAccount] = []
    
    @classmethod
    def get_total_balance(cls) -> float:
        """Calculate total balance across all accounts.
        
        Returns:
            float: Sum of all account balances, or 0.0 if no accounts exist.
        """
        ...
```

### Python Type Hints (Controller Layer)

```python
from typing import Dict, Any

@router.get("/total", response_model=Dict[str, Any])
def get_total_balance() -> Dict[str, Any]:
    """Get total balance across all bank accounts.
    
    Returns:
        dict: Response containing total_balance and currency.
    """
    ...
```

## Future Considerations (Out of Scope for MVP)

- **Multi-currency support**: Would require currency conversion rates
- **Balance history**: Would require time-series data storage
- **Filtered totals**: Would require query parameters and filtering logic
- **Breakdown by account type**: Would require account categorization
- **Caching**: Would require cache invalidation strategy

## Summary

This feature introduces minimal data model complexity:
- **0 new persistent entities**
- **1 new response DTO** (TotalBalanceResponse)
- **0 database changes**
- **Reuses existing BankAccount entity**

The simplicity aligns with the MVP approach and constitutional principles of starting simple and avoiding premature optimization.
