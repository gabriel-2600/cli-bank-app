# CLI Bank App

## Description

A simple **command-line banking application** built to refresh Core Java, OOP, and SQL fundamentals while practicing SOLID principles—specifically **Single Responsibility Principle (SRP)**, **Open/Closed Principle (OCP)**, and **Dependency Inversion Principle (DIP)**.  
The project focuses on clean design, separation of concerns, and testable code rather than on full production-ready features.

## Tech Stack

- **Java**
- **MySQL**
- **IntelliJ IDEA**
- **Cursor**

## Branches

- **`main`**: Contains improved problem solving and concept refreshes, with **DIP** applied to decouple high-level logic from low-level implementations.
- **`experiment/agentic-coding`**: Used to explore and implement **SRP**, **OCP**, and custom exception handling while experimenting with agentic coding workflows.

## Database

- **Schema**: See `database/schema.sql` for the full DDL.
- **Core tables (example)**:
  - **`users`**
    - `user_id` (primary key)
    - basic user details (e.g. name, email)
  - **`accounts`**
    - `account_id` (primary key)
    - `user_id` (foreign key to `users.user_id`)
    - account info (e.g. balance, account_type)

| Table          | Key columns / purpose                                                                                   |
| -------------- | ------------------------------------------------------------------------------------------------------- |
| `users`        | `user_id` (PK), `full_name`, `username` (unique), `password`, `created_at`                              |
| `accounts`     | `account_id` (PK), `user_id` (FK to `users.user_id`), `account_name`, `balance`                         |
| `transactions` | `transaction_id` (PK), `account_id` (FK to `accounts.account_id`), `amount`, `transaction_type`         |
| `transfers`    | `transfer_id` (PK), `transaction_id` (FK to `transactions.transaction_id`), `recipient_account_id` (FK) |

For the exact schema and constraints, see `database/schema.sql`.

## How to set up

1. **Clone the project**
   - `git clone <repo-url>`
   - `cd cli-bank-app`
2. **Set up the database**
   - Make sure MySQL is running.
   - From the `database` folder, run:  
     `mysql -u <your_username> -p <your_database_name> < schema.sql`
3. **Configure environment**
   - Create your own `.env` to set up necessary credentials

## Tree

```text
.
├── bank_app.iml
├── database
│   └── schema.sql
├── pom.xml
├── README.md
├── src
│   ├── AGENTS.md
│   └── main
│       └── java
│           ├── exceptions
│           ├── Main.java
│           ├── model
│           ├── repository
│           ├── service
│           ├── utilities
│           └── view
├── system.env
```
