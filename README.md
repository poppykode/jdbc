# Multi-Database Spring Boot Web Service with Flyway and Swagger

This project demonstrates a Spring Boot web service that connects to two separate databases (MySQL and PostgreSQL), manages database migrations with Flyway, uses JdbcTemplate for database interactions, and provides Swagger documentation for its API.  It features two related entities: `Post` (stored in MySQL) and `Comment` (stored in PostgreSQL).

## Project Overview

This web service allows users to manage posts and their associated comments.  Posts are stored in a MySQL database, while comments are stored in a PostgreSQL database.  This separation demonstrates how to work with multiple database systems within a single Spring Boot application.

## Technologies Used

*   **Spring Boot:** Core framework for building the application.
*   **MySQL:** Database for storing `Post` entities.
*   **PostgreSQL:** Database for storing `Comment` entities.
*   **Flyway:** Database migration tool for managing schema changes.
*   **JdbcTemplate:** Spring's JDBC module for database interaction.
*   **Swagger:** API documentation generator.
*   **Maven/Gradle:** Build management tool.  *(Specify which one you used)*

## Getting Started

### Prerequisites

*   Java 21 (or your chosen version)
*   Maven/Gradle (your chosen build tool)
*   Docker (Optional, but recommended for easy database setup)

### Database Setup (Recommended using Docker)

1.  **MySQL:**

    ```bash
    docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=your_mysql_root_password -p 3306:3306 -d mysql:latest
    ```

2.  **PostgreSQL:**

    ```bash
    docker run --name postgres-container -e POSTGRES_USER=your_postgres_user -e POSTGRES_PASSWORD=your_postgres_password -e POSTGRES_DB=your_postgres_db -p 5432:5432 -d postgres
    ```

    Replace `your_mysql_root_password`, `your_postgres_user`, `your_postgres_password`, and `your_postgres_db` with your desired credentials.

### Build and Run

1.  Clone the repository:

    ```bash
    git clone https://github.com/poppykode/jdbc.git
    ```

2.  Navigate to the project directory:

    ```bash
    cd jdbc
    ```

3.  Build the application:

    ```bash
    mvn clean install  # For Maven
    ./gradlew build  # For Gradle
    ```

4.  Run the application:

    ```bash
    mvn spring-boot:run  # For Maven
    ./gradlew bootRun # For Gradle
    ```

### API Documentation (Swagger)

The API documentation is available at: `http://localhost:1989/swagger-ui.html` (or your configured port).

## Database Migrations (Flyway)

Flyway migrations are located in the `src/main/resources/db/migration/mysql and src/main/resources/db/migration/postgres` directory.  The migrations are automatically applied when the application starts.

