# Quickstart Guide: Total Balance API

**Feature**: Total Balance API  
**Endpoint**: `GET /api/BankAccount/total`  
**Version**: 1.0.0

## Overview

The Total Balance API provides a single endpoint to calculate and retrieve the aggregate balance across all bank accounts. This guide will help you quickly integrate and use this endpoint.

## Prerequisites

- Bank Account API server running (default: `http://localhost:3000`)
- At least one bank account created (or test with empty state)
- HTTP client (curl, Postman, browser, or your application)

## Quick Start

### 1. Basic Request

**Using curl:**
```bash
curl http://localhost:3000/api/BankAccount/total
```

**Using JavaScript/fetch:**
```javascript
fetch('http://localhost:3000/api/BankAccount/total')
  .then(response => response.json())
  .then(data => console.log(data));
```

**Using Python/requests:**
```python
import requests

response = requests.get('http://localhost:3000/api/BankAccount/total')
data = response.json()
print(f"Total Balance: ${data['total_balance']:.2f} {data['currency']}")
```

### 2. Expected Response

**Success Response (HTTP 200):**
```json
{
  "total_balance": 1234567.89,
  "currency": "USD",
  "account_count": 42
}
```

**Response Fields:**
- `total_balance` (number): Sum of all account balances
- `currency` (string): Currency code (always "USD" in current version)
- `account_count` (number): Total number of accounts included in the calculation

### 3. Common Scenarios

#### Scenario A: Bank with Multiple Accounts

**Setup:**
```bash
# Create test accounts
curl -X POST http://localhost:3000/api/BankAccount \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "12345", "accountHolderName": "Alice", "balance": 1000.00}'

curl -X POST http://localhost:3000/api/BankAccount \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "12346", "accountHolderName": "Bob", "balance": 2500.50}'
```

**Get Total:**
```bash
curl http://localhost:3000/api/BankAccount/total
```

**Expected Result:**
```json
{
  "total_balance": 3500.50,
  "currency": "USD"
}
```

#### Scenario B: Empty Bank (No Accounts)

**Request:**
```bash
curl http://localhost:3000/api/BankAccount/total
```

**Expected Result:**
```json
{
  "total_balance": 0.0,
  "currency": "USD"
}
```

#### Scenario C: Mixed Positive and Negative Balances

**Setup:**
```bash
# Account with positive balance
curl -X POST http://localhost:3000/api/BankAccount \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "12345", "accountHolderName": "Alice", "balance": 5000.00}'

# Overdrawn account (negative balance)
curl -X POST http://localhost:3000/api/BankAccount \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "12346", "accountHolderName": "Bob", "balance": -250.00}'
```

**Get Total:**
```bash
curl http://localhost:3000/api/BankAccount/total
```

**Expected Result:**
```json
{
  "total_balance": 4750.0,
  "currency": "USD"
}
```

## Integration Examples

### React Application

```typescript
import { useState, useEffect } from 'react';

interface TotalBalance {
  total_balance: number;
  currency: string;
}

function TotalBalanceDisplay() {
  const [balance, setBalance] = useState<TotalBalance | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetch('http://localhost:3000/api/BankAccount/total')
      .then(response => {
        if (!response.ok) throw new Error('Failed to fetch total balance');
        return response.json();
      })
      .then(data => {
        setBalance(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading total balance...</div>;
  if (error) return <div>Error: {error}</div>;
  
  return (
    <div className="total-balance">
      <h2>Total Bank Balance</h2>
      <p className="amount">
        {balance?.total_balance.toLocaleString('en-US', {
          style: 'currency',
          currency: balance?.currency
        })}
      </p>
    </div>
  );
}

export default TotalBalanceDisplay;
```

### Python Dashboard

```python
import requests
from typing import Dict, Any

class BankDashboard:
    def __init__(self, api_url: str = "http://localhost:3000"):
        self.api_url = api_url
    
    def get_total_balance(self) -> Dict[str, Any]:
        """Fetch total balance from the API."""
        response = requests.get(f"{self.api_url}/api/BankAccount/total")
        response.raise_for_status()
        return response.json()
    
    def display_summary(self) -> None:
        """Display bank summary with total balance."""
        try:
            total = self.get_total_balance()
            balance = total['total_balance']
            currency = total['currency']
            
            print("=" * 40)
            print("BANK SUMMARY")
            print("=" * 40)
            print(f"Total Balance: ${balance:,.2f} {currency}")
            print("=" * 40)
            
        except requests.RequestException as e:
            print(f"Error fetching balance: {e}")

# Usage
dashboard = BankDashboard()
dashboard.display_summary()
```

## Testing the Endpoint

### Manual Testing with curl

```bash
# 1. Check total with empty database
curl -v http://localhost:3000/api/BankAccount/total

# 2. Create some accounts
curl -X POST http://localhost:3000/api/BankAccount \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "ACC001", "accountHolderName": "Test User 1", "balance": 1000.00}'

curl -X POST http://localhost:3000/api/BankAccount \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "ACC002", "accountHolderName": "Test User 2", "balance": 500.50}'

# 3. Verify total updated
curl http://localhost:3000/api/BankAccount/total

# 4. Update an account balance
curl -X PUT http://localhost:3000/api/BankAccount/1 \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "accountNumber": "ACC001", "accountHolderName": "Test User 1", "balance": 2000.00}'

# 5. Verify total reflects the update
curl http://localhost:3000/api/BankAccount/total
```

### Automated Testing with pytest

```python
import pytest
from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_total_balance_empty():
    """Test total balance with no accounts."""
    response = client.get("/api/BankAccount/total")
    assert response.status_code == 200
    data = response.json()
    assert data["total_balance"] == 0.0
    assert data["currency"] == "USD"

def test_total_balance_multiple_accounts():
    """Test total balance with multiple accounts."""
    # Create test accounts
    client.post("/api/BankAccount", json={
        "accountNumber": "12345",
        "accountHolderName": "Alice",
        "balance": 1000.00
    })
    client.post("/api/BankAccount", json={
        "accountNumber": "12346",
        "accountHolderName": "Bob",
        "balance": 500.00
    })
    
    # Get total
    response = client.get("/api/BankAccount/total")
    assert response.status_code == 200
    data = response.json()
    assert data["total_balance"] == 1500.00
```

## Performance Considerations

- **Response Time**: < 2 seconds for up to 10,000 accounts
- **Concurrency**: Supports 100+ concurrent requests
- **Caching**: Not implemented in MVP (direct calculation each time)
- **Recommended Usage**: For dashboards that refresh periodically, not real-time updates

## Troubleshooting

### Issue: 404 Not Found

**Problem**: Endpoint doesn't exist  
**Solution**: Ensure you're using the correct URL path `/api/BankAccount/total`

```bash
# Correct
curl http://localhost:3000/api/BankAccount/total

# Incorrect
curl http://localhost:3000/api/BankAccount/balance  # Wrong path
```

### Issue: Connection Refused

**Problem**: API server not running  
**Solution**: Start the backend server

```bash
cd bank-account-api-py
python3 -m uvicorn app.main:app --reload --port 3000
```

### Issue: Unexpected Total

**Problem**: Total doesn't match expected value  
**Solution**: Verify individual account balances

```bash
# List all accounts
curl http://localhost:3000/api/BankAccount

# Calculate total manually and compare
```

### Issue: 500 Internal Server Error

**Problem**: Server-side error during calculation  
**Solution**: Check server logs for details

```bash
# Server logs will show the stack trace
# Common causes: data corruption, type errors
```

## API Reference Summary

| Property | Value |
|----------|-------|
| **Method** | GET |
| **Path** | `/api/BankAccount/total` |
| **Parameters** | None |
| **Response Type** | application/json |
| **Success Code** | 200 OK |
| **Error Code** | 500 Internal Server Error |

## Next Steps

1. **Explore the full API**: See [contracts/total-balance-api.yaml](contracts/total-balance-api.yaml)
2. **View data models**: See [data-model.md](data-model.md)
3. **Implementation details**: See [plan.md](plan.md)
4. **Run full test suite**: `cd bank-account-api-py && pytest tests/`

## Support

For issues or questions:
- Check the [specification](spec.md) for feature requirements
- Review the [implementation plan](plan.md) for technical details
- Run tests to verify setup: `pytest tests/`

---

**Last Updated**: 2026-01-14  
**API Version**: 1.0.0  
**Feature Branch**: 001-total-balance
