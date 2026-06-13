# Decisions

**Name:** Paresh SSG

**Date started:** 13-06-2026

**Date submitted:** 13-06-2026

I started by running the application and test suite to understand the current state of the project. I prioritized compilation errors and failing tests first because they directly impacted functionality and helped identify the most important issues.

---

## 1. Code & Design Decisions

**The codebase includes an `Auditable` abstract class that is not currently used by any entity. What did you do with it, if anything? Walk through your reasoning — what is the purpose of the Auditable pattern, what are the tradeoffs of using it versus not, and why did you make the choice you did?**

I did not modify the Auditable class. Its purpose is to provide common audit fields such as createdAt and updatedAt for multiple entities. The advantage is reduced code duplication and consistent auditing across entities. Since it was not directly related to the failing functionality, I chose not to change it during this task.

**`TransactionResponse` is used as the outbound DTO for the API. What changes did you make to it, if any? Why does the shape of a response DTO matter — and what is the risk of returning an entity directly from a controller?**

I did not make changes to TransactionResponse. DTOs help control exactly what data is exposed to API consumers. Returning entities directly can expose internal fields, create tight coupling between database models and APIs, and cause issues with serialization or future schema changes.

**The `BudgetCalculator` requires grouping and sorting data. What data structure or approach did you choose to implement it? Walk through the alternatives you considered and why you landed where you did.**

I used Java Streams with groupingBy to aggregate spending by category. After aggregation, I sorted entries by total amount in descending order and stored the result in a LinkedHashMap to preserve ordering. This approach is concise, readable, and efficient for the current requirements.

**Were there any decisions you made that are not covered by the questions above? Describe the most significant one and your reasoning.**

The most significant decision was fixing only the issues directly affecting compilation and test failures. This ensured the application became stable and all tests passed before considering additional improvements.

---

## 2. Bug Fixes & Issues Found

**Describe each problem you found in the codebase. For each one: where was it, how did you identify it, what did it cause, and how did you fix it?**

1. TransactionServiceImpl referenced a repository method that did not exist. This caused compilation failure. I replaced the logic with filtering using existing repository methods.

2. The monthly spend calculation excluded transactions on the first day of the month due to an incorrect date comparison. I modified the filter to include the start date boundary.

3. BudgetCalculator was not implemented and always returned an empty map. I implemented grouping, summation, sorting, and top-N filtering.

**Were there any problems you noticed but chose not to fix? If so, explain why.**

The getTransactionsByDateRange method still contains a TODO. I did not implement it because it was not required to make the existing tests pass and was outside the immediate scope.

---

## 3. Testing Decisions

**What tests did you write in `TransactionCandidateTest.java`? For each test, explain what behaviour it validates and why you chose to cover that behaviour.**

I relied primarily on the provided tests. They validated monthly spend calculations, category aggregation, and transaction service behavior. These tests directly covered the issues I fixed.

**What did you deliberately not test, and why? If you had more time, what would be the next most important test to add?**

I did not add controller integration tests. If I had more time, I would add MockMvc tests for API endpoints and validation behavior.

**What is the difference between what `TransactionServiceTest` covers and what your `TransactionCandidateTest` covers? Are they testing the same things?**

TransactionServiceTest focuses on service-layer business logic. Candidate tests are intended to validate behavior from a broader perspective and catch regressions. They complement each other rather than testing exactly the same things.

---

## 4. AI Tool Usage

**Which AI tools did you use? (e.g. ChatGPT, Claude, GitHub Copilot, Cursor, other)**

I used ChatGPT.

**Give two or three specific examples of how you used AI on this project.**

I used AI to understand compilation errors, identify failing logic in monthly spend calculations, and implement the BudgetCalculator method. I reviewed and verified all suggestions by running the test suite.

**Describe a moment where AI gave you something wrong, incomplete, or subtly misleading. How did you catch it, and what did you do?**

An AI suggestion initially caused syntax issues due to misplaced braces. I identified the issue through compiler errors and manually corrected the implementation.

**What is your general philosophy on using AI when writing backend code? Where does it help, and where do you not trust it?**

AI is useful for debugging, code explanations, and implementation ideas. However, I always verify outputs through testing because AI can introduce logic or syntax errors.

---

## 5. What You'd Do Next

**If you had two more days on this project, what would you build or fix first?**

1. Implement getTransactionsByDateRange.
2. Add controller integration tests.
3. Improve repository queries for filtering and aggregation.
4. Add validation for API inputs.

**What is the biggest remaining risk or weakness in the code you have submitted?**

The biggest remaining risk is incomplete functionality in methods that still contain TODOs and the limited integration-level test coverage.
