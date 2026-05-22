# Bank Management System

A secure banking backend REST API built with Spring Boot, Spring Security, and JWT authentication.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL
- Maven
- Postman (for testing)

## Features
- User registration and login with JWT authentication
- Account creation and management
- Deposit, Withdrawal, and Fund Transfer
- Transaction history tracking
- Global exception handling with @ControllerAdvice
- DTO pattern for clean API responses

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login and get JWT token |
| POST | /api/accounts | Create bank account |
| GET | /api/accounts/{id} | Get account details |
| POST | /api/accounts/deposit | Deposit amount |
| POST | /api/accounts/withdraw | Withdraw amount |
| POST | /api/accounts/transfer | Transfer between accounts |
| GET | /api/transactions/{accountId} | Get transaction history |

## How to Run
1. Clone the repo
2. Create a MySQL database named `bankapp`
3. Update `application.properties` with your DB username and password
4. Run the project using `mvn spring-boot:run`
5. Test endpoints using Postman

## Architecture
Follows Controller → Service → Repository layered architecture with clear separation of concerns.

## Project Structure
src/main/java/com/vaibhav/bankapp/
├── controller/    → REST API endpoints
├── service/       → Business logic
├── repository/    → Database operations
├── entity/        → JPA entities
├── dto/           → Data Transfer Objects
├── security/      → JWT filter & config
└── exception/     → Global exception handling
