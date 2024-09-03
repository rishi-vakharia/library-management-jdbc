# Library Management System

## Overview
This project is a Library Management System implemented in Java using JDBC for database connectivity and MySQL as the database. It provides functionality for students, librarians, and super admins to manage books, users, and library operations.

## Features
- User roles: Student, Librarian, and Super Admin
- Book management: Add, delete, issue, and return books
- User management: Add and delete students and librarians
- Authentication system for librarians and super admins
- List available books and all books in the library
- List students and librarians

## Prerequisites
- Java Development Kit (JDK)
- MySQL Server
- MySQL Connector/J (JDBC driver for MySQL)

## Setup Instructions

### Database Setup
1. Open MySQL command prompt and run the following commands:
   ```sql
   CREATE USER 'user1'@'localhost' IDENTIFIED BY 'password';
   source sql/Library_create.sql
   source sql/Library_alter.sql
   source sql/Library_data.sql
   ```

### Compilation and Execution
1. Compile the Java file:
   ```
   javac Library.java
   ```
2. Run the program:
   ```
   java -classpath "mysql-connector-java-8.0.18.jar:." Library
   ```

### Cleanup (Optional)
To drop the database after use:
```sql
source sql/Library_drop.sql
```

## Default Credentials

### Super Admin
- ID: a1
- Password: 1234

### Librarian
- ID: l1
- Password: 1234

## Screenshots

![Screenshot 2024-09-04 015821](.\screenshots\Screenshot 2024-09-04 015821.png)

![Screenshot 2024-09-04 015837](.\screenshots\Screenshot 2024-09-04 015837.png)

![Screenshot 2024-09-04 015901](.\screenshots\Screenshot 2024-09-04 015901.png)

![Screenshot 2024-09-04 015913](.\screenshots\Screenshot 2024-09-04 015913.png)

![Screenshot 2024-09-04 015924](.\screenshots\Screenshot 2024-09-04 015924.png)

![Screenshot 2024-09-04 015940](.\screenshots\Screenshot 2024-09-04 015940.png)

![Screenshot 2024-09-04 015952](.\screenshots\Screenshot 2024-09-04 015952.png)

## Project Structure

- `Library.java`: Main Java file containing the application logic
- `sql/Library_create.sql`: SQL script to create the database and tables
- `sql/Library_alter.sql`: SQL script to add foreign key constraints
- `sql/Library_data.sql`: SQL script to insert initial data
- `sql/Library_drop.sql`: SQL script to drop the database (for cleanup)

## UML Diagrams

![Screenshot 2024-09-04 015655](.\screenshots\Screenshot 2024-09-04 015655.png)

## Notes
- The system uses a console-based interface for interaction.
- Proper error handling and input validation are implemented throughout the application.
- The project follows object-oriented design principles and utilizes JDBC for database operations.
