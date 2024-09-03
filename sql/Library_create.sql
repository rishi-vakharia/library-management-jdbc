CREATE DATABASE Library;
USE Library;

GRANT ALL PRIVILEGES ON Library.* TO 'user1'@'localhost';

CREATE TABLE student (
    roll_number VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    book_id VARCHAR(100),
    CONSTRAINT pk_student PRIMARY KEY (roll_number)
);

CREATE TABLE book (
    book_id VARCHAR(100) NOT NULL,
    book_name VARCHAR(100) NOT NULL,
    book_author VARCHAR(100) NOT NULL,
    publication_year INTEGER NOT NULL,
    issuer VARCHAR(100),
    CONSTRAINT pk_book PRIMARY KEY (book_id)
);

CREATE TABLE librarian (
    librarian_id VARCHAR(100) NOT NULL,
    librarian_name VARCHAR(100) NOT NULL,
    librarian_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_librarian PRIMARY KEY (librarian_id)
);

CREATE TABLE super_admin (
    super_admin_id VARCHAR(100) NOT NULL,
    super_admin_name VARCHAR(100) NOT NULL,
    super_admin_password VARCHAR(100) NOT NULL,
    CONSTRAINT pk_super_admin PRIMARY KEY (super_admin_id)
);