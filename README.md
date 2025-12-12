# Product CRUD --- Quarkus Unit Testing Example

This repository contains the example code used in an article about how
to write effective **unit tests in Quarkus**.\
It implements a simple Product CRUD and demonstrates how to structure,
isolate, and test business logic using the **AAA pattern (Arrange ---
Act --- Assert)**.

## 🧪 Purpose

The goal of this project is to serve as a clean and minimal codebase to
teach:

-   How to write **unit tests** in Quarkus
-   How to mock dependencies using **Mockito**
-   How to test business rules instead of just REST endpoints
-   How to organize test classes following best practices
-   How to structure code to make testing easier

### Available Branches

- **main**: main: contains the complete CRUD implementation and only the tests for the `create` method.
  Ideal for readers following the article who want to practice writing the remaining tests on their own.

- **full-tests**: includes all the finished tests (create, update, delete, findAll, findById, business rules, and exceptions).
  Use this branch to compare your solution or study more advanced test scenarios.

## 🚀 Running the Application

Run in development mode:

``` bash
./mvnw quarkus:dev
```

Quarkus Dev UI:

    http://localhost:8080/q/dev/

Quarkus Swagger UI:

    http://localhost:8080/swagger-ui

## 🧪 Running Tests

To execute all unit tests:

``` bash
./mvnw test
```

The tests follow the **AAA structure**:

-   **Arrange:** prepare mocks, inputs, and scenarios\
-   **Act:** call the method under test\
-   **Assert:** verify results, interactions, and exceptions

## 📂 Project Structure

    src/
     ├── main/java/.../ferrs
     │     ├── dtos 
     │     │     ├──ProductRequestDto.java
     │     │     └──ProductResponseDto.java
     │     ├── entities 
     │     │     └──Product.java
     │     ├── repository 
     │     │     └──ProductRepository.java
     │     ├── resource 
     │     │     └──ProductResource.java
     │     └── service
     │           └──ProductService.java
     └── test/java/.../ferrs/service
           └── ProductServiceTest.java

## 🧰 Tech Stack

-   **Java 17**
-   **Quarkus**
-   **RESTEasy Reactive**
-   **Hibernate ORM with Panache**
-   **H2 Database**
-   **JUnit 5**
-   **Mockito**

## 📦 Packaging the Application

Package:

``` bash
./mvnw package
```

Run:

``` bash
java -jar target/quarkus-app/quarkus-run.jar
```

Build an uber-jar:

``` bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

## ❄️ Native Build (Optional)

``` bash
./mvnw package -Dnative
```

Or using container-based native build:

``` bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

## 📄 About the Article

This codebase was created exclusively to support an article explaining
**how to create clean and maintainable unit tests in Quarkus**, focusing
on correctness, clarity, and best testing practices.

------------------------------------------------------------------------

If you find this project useful, feel free to ⭐ star the repository!
