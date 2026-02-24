# Refactor Plan: SRP Split for Transaction Repository

This plan separates **account balance / lifecycle** operations from **transaction history** operations so each repository has a single reason to change.

---

## Current State


| Location                                | Responsibility (mixed)                                                                                 |
| --------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| **TransactionRepoInterface**            | Account balance (deposit, withdraw, transfer), account deletion, transaction records, transfer records |
| **TransactionRepoImplementation**       | JDBC for all of the above                                                                              |
| **TransactionImplementation** (service) | Uses one repo for everything                                                                           |


**Callers:** `TransactionImplementation` (service) and `TransactionFeaturesView` (instantiates repos and service).

---

## Target State


| Component                    | Single responsibility                                                                                                    |
| ---------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| **AccountRepoInterface**     | Account lifecycle and balance: create, read, update balance (deposit/withdraw/transfer), delete.                         |
| **TransactionRepoInterface** | Transaction and transfer **audit data only**: create transaction row, create transfer row, retrieve transaction history. |


The **service** (`TransactionImplementation`) will orchestrate both: e.g. "deposit" = update account balance (AccountRepo) + create transaction record (TransactionRepo).

---

## Step-by-Step Refactor

### Step 1: Extend AccountRepoInterface

**File:** `src/main/java/repository/account/AccountRepoInterface.java`

Add these method signatures (keep existing methods unchanged):

```java
void addToBalance(int accountID, double amount) throws SQLException;
void subtractFromBalance(int accountID, double amount) throws SQLException;
void transferBetweenAccounts(int accountID, int recipientAccountID, double amount) throws SQLException;
void deleteAccount(int accountID) throws SQLException;
```

- Add `import java.sql.SQLException;`.
- Naming: `addToBalance` / `subtractFromBalance` match "account balance" responsibility; `transferBetweenAccounts` keeps transfer as one logical operation (two balance updates in one place). `deleteAccount` replaces `deleteBankAccount` naming.

---

### Step 2: Implement new methods in AccountRepoImplementation

**File:** `src/main/java/repository/account/AccountRepoImplementation.java`

- Implement the four new methods by **moving** the corresponding SQL and JDBC logic from `TransactionRepoImplementation`:
  - `depositInDatabase` → `addToBalance`
  - `withdrawAmount` → `subtractFromBalance`
  - `transferMoney` → `transferBetweenAccounts`
  - `deleteBankAccount` → `deleteAccount`
- Keep the same behavior: same SQL, same transaction/rollback for `transferBetweenAccounts`, same exception handling style you use elsewhere in this class (e.g. rethrow as `RuntimeException` or declare `SQLException` and rethrow — be consistent with existing methods in this file).
- Add `throws SQLException` to the new methods if the interface declares it.

---

### Step 3: Slim down TransactionRepoInterface

**File:** `src/main/java/repository/transaction/TransactionRepoInterface.java`

- **Remove** these methods:
  - `depositInDatabase`
  - `withdrawAmount`
  - `transferMoney`
  - `deleteBankAccount`
- **Keep** only:
  - `createTransactionData(int accountID, double amount, String transactionType)`
  - `retrieveTransaction(int accountID)` (consider renaming to `retrieveTransactions` later for clarity)
  - `createTransferData(int transactionID, int recipientAccountID)`

---

### Step 4: Slim down TransactionRepoImplementation

**File:** `src/main/java/repository/transaction/TransactionRepoImplementation.java`

- **Remove** the implementations of `depositInDatabase`, `withdrawAmount`, `transferMoney`, and `deleteBankAccount`.
- **Keep** only the implementations of `createTransactionData`, `retrieveTransaction`, and `createTransferData`.

---

### Step 5: Update TransactionImplementation (service) to use both repos

**File:** `src/main/java/service/transaction/TransactionImplementation.java`

- Add a second dependency: `AccountRepoInterface accountRepository`.
- Constructor: `TransactionImplementation(TransactionRepoInterface transactionRepository, AccountRepoInterface accountRepository)`.
- **depositInAccount:**  
Call `accountRepository.addToBalance(accountID, amount)` then `transactionRepository.createTransactionData(...)` (same as now, but balance update comes from account repo).
- **withdrawInAccount:**  
Call `accountRepository.subtractFromBalance(accountID, amount)` then `transactionRepository.createTransactionData(...)`.
- **transferToAnAccount:**  
Call `accountRepository.transferBetweenAccounts(accountID, recipientAccountID, amount)` then create transaction and transfer via `transactionRepository.createTransactionData` and `transactionRepository.createTransferData`.
- **deleteAccount:**  
Call `accountRepository.deleteAccount(accountID)` only (no transaction record needed for "account deleted" unless you add that later).
- **createTransaction / viewTransactionHistory / createTransfer:**  
Keep using only `transactionRepository` (no change in behavior).

Result: account balance and lifecycle live in `AccountRepo`*; transaction/transfer audit data stay in `TransactionRepo*`.

---

### Step 6: Update TransactionFeaturesView (and any other callers)

**File:** `src/main/java/view/dashboard/transaction/TransactionFeaturesView.java`

- Where you currently have:
  - `TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();`
  - `TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository);`
- Change to:
  - `AccountRepoInterface accountRepository = new AccountRepoImplementation();`
  - `TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();`
  - `TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository, accountRepository);`
- Do this in every method that constructs `TransactionImplementation` (e.g. `depositView`, `withdrawView`, `transferView`, `deleteAccountView`, and the one that shows transaction history) so they all use the same two-repo wiring.

---

## Optional follow-ups (after this refactor)

- **Naming:** Rename `retrieveTransaction` → `retrieveTransactions` in the transaction repo (interface + impl) for clarity.
- **Exception handling:** In `TransactionRepoImplementation`, make exception handling consistent (e.g. remove `e.printStackTrace()` and either rethrow or log in one place).
- **transferMoney exception type:** In the account repo's `transferBetweenAccounts`, throw `SQLException` (or a domain exception) instead of `RuntimeException` if you want the interface to declare checked exceptions consistently.

---

## Summary


| Step | Action                                                                                                                             |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------- |
| 1    | Add 4 methods to `AccountRepoInterface`                                                                                            |
| 2    | Implement them in `AccountRepoImplementation` (move SQL from transaction repo)                                                     |
| 3    | Remove 4 methods from `TransactionRepoInterface`                                                                                   |
| 4    | Remove 4 method bodies from `TransactionRepoImplementation`                                                                        |
| 5    | Inject `AccountRepoInterface` into `TransactionImplementation` and route balance/delete to account repo, audit to transaction repo |
| 6    | In `TransactionFeaturesView`, instantiate both repos and pass both into `TransactionImplementation`                                |


After this, **AccountRepo** has one reason to change (account schema/lifecycle/balance), and **TransactionRepo** has one reason to change (transaction/transfer audit schema and queries).