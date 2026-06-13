# Questions

## Java & Object-Oriented Design

### 1.

`@MappedSuperclass` tells JPA that the fields in the parent class should be inherited by entity classes and mapped into their database tables. If it were removed, the inherited fields would not be automatically mapped. Using a shared Auditable class avoids code duplication and ensures consistency across multiple entities.

### 2.

Interfaces improve flexibility and maintainability. Even with one implementation today, another implementation may be needed later for testing or different business logic. The interface also helps enforce a clear contract between layers.

### 3.

`@Enumerated(EnumType.STRING)` stores enum values as readable text such as FOOD or TRANSPORT in the database. If a new enum value is added without considering existing data and migrations, application behavior may become inconsistent. String storage is safer than ordinal values because it is easier to understand and maintain.

### 4.

This is a utility class pattern. The class is final and has a private constructor to prevent instantiation. I used a Map for grouping category totals and a LinkedHashMap for preserving the sorted order of the final results.

---

## Spring Boot & REST API Design

### 5.

Returning an entity directly exposes internal database structures to API consumers. A DTO provides control over what data is returned and helps prevent accidental exposure of fields. DTOs also make future API changes easier without affecting database entities.

### 6.

The request first reaches the controller, which validates input and forwards it to the service layer. The service handles business logic and calls the repository. The repository interacts with the database through JPA. Without `@Valid`, invalid request data could reach the service layer and cause incorrect data to be stored.

### 7.

Although all three annotations register beans, they communicate different responsibilities. `@RestController` handles HTTP requests, `@Service` contains business logic, and `@Repository` manages data access. This separation improves readability and maintainability.

### 8.

The endpoint should return HTTP 400 Bad Request for an invalid month such as 13. Validation can be handled using annotations like `@Min(1)` and `@Max(12)` or custom validation logic. The controller is typically responsible for validating request parameters.

---

## Data Access & SQL

### 9.

Spring Data JPA analyzes repository method names during application startup and automatically generates the required queries. For example, `findByAccountId` becomes a query that filters by accountId. I would use `@Query` when the query is too complex for method-name derivation.

### 10.

The bug excluded transactions occurring on the first day of the month because the code used `isAfter(startOfMonth)`. As a result, transactions exactly on the first day were ignored. This was fixed by making the comparison inclusive. Date boundary bugs are common because developers often overlook whether endpoints should be inclusive or exclusive.

### 11.

For PostgreSQL, I would add the PostgreSQL dependency in `pom.xml` and update datasource properties in `application.properties`. The JDBC URL, username, password, and driver class would be configured for PostgreSQL. Using `ddl-auto=create-drop` in production is risky because it can delete existing data whenever the application restarts.

---

## Testing

### 12.

Mockito replaces the real repository with a mock object. This allows testing service logic without connecting to a database. The tests verify business logic and interactions. They cannot catch database-specific issues or repository query problems.

### 13.

I do not agree completely. Controller tests can verify request mappings, validation, HTTP status codes, and JSON responses. A MockMvc test could catch an incorrect endpoint mapping that service tests would never detect.

### 14.

I focused first on understanding the existing tests and fixing the failures they exposed. The first priority was the monthly spend calculation because it directly caused a failing test. This approach helped resolve critical issues quickly and systematically.

---

## AI & Modern Engineering

### 15.

I used ChatGPT during the project. I asked for help understanding compiler errors, implementing BudgetCalculator, and identifying why tests were failing. I accepted suggestions after verifying them through compilation and testing. AI was trustworthy for explaining concepts but required careful review when generating implementation code because syntax and logic errors were possible.
