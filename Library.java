import java.sql.*;
import java.util.Scanner;

public class Library {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Library?allowPublicKeyRetrieval=true&useSSL=false";
	static final String USER = "user1";
	static final String PASS = "password";

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			Scanner scan = new Scanner(System.in);

			clearScreen();
			System.out.println("\nWELCOME TO LIBRARY MANAGEMENT SYSTEM\n");
			main_menu(stmt, scan);

			scan.close();
			stmt.close();
			conn.close();
		} 
		catch (SQLException se) { 
			se.printStackTrace();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		} 
		finally {
			try {
				if (stmt != null)
				stmt.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null)
				conn.close();
			} 
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	static void main_menu(Statement stmt, Scanner scan) {

		System.out.println("Login as:");
		System.out.println("1. Student");
		System.out.println("2. Librarian");
		System.out.println("3. Super Admin");
		System.out.println("0. Exit");

		System.out.print("\n\nENTER YOUR CHOICE: ");
		int choice = Integer.parseInt(scan.nextLine());
		clearScreen();

		switch (choice) {
			case 0:
				System.out.println("\nThank you for using the System!!\n\n");
				System.exit(0);
			case 1:
				student_menu(stmt, scan);
				break;
			case 2:
				check_librarian(stmt, scan);
				break;
			case 3:
				check_super_admin(stmt, scan);
				break;
			default:
				clearScreen();
				System.out.println("Please Enter a Valid Choice!!\n");
				break;
		}

		main_menu(stmt, scan);
	}

	static void student_menu(Statement stmt, Scanner scan) {

		System.out.println("Please select required option:");
		System.out.println("1. List of available books");
		System.out.println("0. Exit");

		System.out.print("\n\nENTER YOUR CHOICE: ");
		int choice = Integer.parseInt(scan.nextLine());
		clearScreen();

		switch (choice) {
			case 0:
				return;
			case 1:
				list_of_books(stmt, scan, true);
				break;
			default:
				clearScreen();
				System.out.println("Please Enter a Valid Choice!!\n");
				break;
		}
		
		student_menu(stmt, scan);
	}

	static boolean authentication(Statement stmt, Scanner scan, boolean isSuperAdmin) {
		System.out.print("Enter your ID: ");
		String id = scan.nextLine();
		System.out.print("Enter your password: ");
		String password = scan.nextLine();

		clearScreen();
		boolean authenticated = false;

		if (isSuperAdmin) {
			String sql = "SELECT * from super_admin";
			ResultSet rs = executeSqlStmt(stmt, sql);

			try {
				while (rs.next()) {
					String possible_id = rs.getString("super_admin_id");
					String possible_password = rs.getString("super_admin_password");

					if (possible_id.equals(id) && password.equals(possible_password)) {
						authenticated = true;
						break;
					}
				}
			} 
			catch (SQLException se) {
			}
		} 
		else {
			String sql = "SELECT * from librarian";
			ResultSet rs = executeSqlStmt(stmt, sql);

			try {
				while (rs.next()) {
					String possible_id = rs.getString("librarian_id");
					String possible_password = rs.getString("librarian_password");

					if (possible_id.equals(id) && password.equals(possible_password)) {
						authenticated = true;
						break;
					}
				}
			} 
			catch (SQLException se) {
			}
		}

		return authenticated;
	}

	static void check_librarian(Statement stmt, Scanner scan) {
		if (authentication(stmt, scan, false)) {
			librarian_menu(stmt, scan);
		} 
		else {
			System.out.print("Entered details were incorrect. Do you want to try again (Y/N)? ");
			String input = scan.nextLine();
			if (input.equals("Y"))
				check_librarian(stmt, scan);
			else
				return;
		}
	}

	static void check_super_admin(Statement stmt, Scanner scan) {
		if (authentication(stmt, scan, true)) {
			super_admin_menu(stmt, scan);
		} 
		else {
			System.out.print("Entered details were incorrect. Do you want to try again (Y/N)? ");
			String input = scan.nextLine();
			if (input.equals("Y"))
				check_super_admin(stmt, scan);
			else
				return;
		}
	}

	static void librarian_menu(Statement stmt, Scanner scan) {

		System.out.println("Please select required option:");
		System.out.println("1. List of all books");
		System.out.println("2. List of available books");
		System.out.println("3. Issue a book");
		System.out.println("4. Return a book");
		System.out.println("5. Add a book");
		System.out.println("6. Delete a book");
		System.out.println("0. Exit");

		System.out.print("\n\nENTER YOUR CHOICE: ");
		int choice = Integer.parseInt(scan.nextLine());
		clearScreen();

		switch (choice) {
			case 0:
				return;
			case 1:
				list_of_books(stmt, scan, false);
				break;
			case 2:
				list_of_books(stmt, scan, true);
				break;
			case 3:
				issue_book(stmt, scan);
				break;
			case 4:
				return_book(stmt, scan);
				break;
			case 5:
				add_book(stmt, scan);
				break;
			case 6:
				delete_book(stmt, scan);
				break;
			default:
				clearScreen();
				System.out.println("Please Enter a Valid Choice!!\n");
				break;
		}
		librarian_menu(stmt, scan);
	}

	static void super_admin_menu(Statement stmt, Scanner scan) {

		System.out.println("Please select required option:");
		System.out.println("1. List of student");
		System.out.println("2. List of librarians");
		System.out.println("3. Add a student");
		System.out.println("4. Delete a student");
		System.out.println("5. Add a librarian");
		System.out.println("6. Delete a librarian");
		System.out.println("0. Exit");

		System.out.print("\n\nENTER YOUR CHOICE: ");
		int choice = Integer.parseInt(scan.nextLine());
		clearScreen();

		switch (choice) {
			case 0:
				return;
			case 1:
				list_of_students(stmt, scan);
				break;
			case 2:
				list_of_librarians(stmt, scan);
				break;
			case 3:
				add_student(stmt, scan);
				break;
			case 4:
				delete_student(stmt, scan);
				break;
			case 5:
				add_librarian(stmt, scan);
				break;
			case 6:
				delete_librarian(stmt, scan);
				break;
			default:
				clearScreen();
				System.out.println("Please Enter a Valid Choice!!\n");
				break;
		}
		super_admin_menu(stmt, scan);
	}

	static boolean list_of_books(Statement stmt, Scanner scan, boolean checkAvailable) {

		String sql = "select * from book";
		ResultSet rs = executeSqlStmt(stmt, sql);
		boolean noBooks = true;

		try {
			System.out.println("List of available books:\n");
			while (rs.next()) {
				String id = rs.getString("book_id");
				String name = rs.getString("book_name");
				String author = rs.getString("book_author");
				Integer year = rs.getInt("publication_year");
				String issuer = rs.getString("issuer");

				if (checkAvailable) {
					if (issuer == null) {
						System.out.println("ID : " + id);
						System.out.println("Book Name: " + name);
						System.out.println("Author : " + author);
						System.out.println("Publication year : " + year);
						System.out.println("");
						noBooks = false;
					}
				} 
				else {
					System.out.println("ID : " + id);
					System.out.println("Book Name: " + name);
					System.out.println("Author : " + author);
					System.out.println("Publication year : " + year);
					System.out.println("Issuer : " + issuer);
					System.out.println("");
					noBooks = false;
				}
			}

			if (noBooks)
				System.out.println("Sorry, no books are available!!\n");

			rs.close();
		} 
		catch (SQLException e) {
			// e.printStackTrace();
		}
		return noBooks;
	}

	static void issue_book(Statement stmt, Scanner scan) {
		try {
			boolean noBooks = list_of_books(stmt, scan, true);
			if (!noBooks) {
				System.out.print("\nEnter book ID : ");
				String id = scan.nextLine();

				System.out.print("Enter student roll number : ");
				String student_roll_no = scan.nextLine();

				clearScreen();

				String sql = String.format("UPDATE book SET issuer = '%s' WHERE book_id = '%s'", student_roll_no, id);
				int result = updateSqlStmt(stmt, sql);

				if (result != 0)
					System.out.println("ISSUER HAS BEEN UPDATED SUCCESFULLY!!\n");
				else
					System.out.println("Something went wrong!\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void return_book(Statement stmt, Scanner scan) {
		try {
			System.out.print("\nEnter book ID : ");
			String id = scan.nextLine();

			clearScreen();

			String sql = String.format("UPDATE book SET issuer = NULL WHERE book_id = '%s'", id);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Book has been returned!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void add_book(Statement stmt, Scanner scan) {
		try {
			System.out.print("\nEnter book ID : ");
			String id = scan.nextLine();
			System.out.print("\nEnter book name : ");
			String name = scan.nextLine();
			System.out.print("\nEnter book author : ");
			String author = scan.nextLine();
			System.out.print("\nEnter book publication year : ");
			Integer year = Integer.parseInt(scan.nextLine());

			clearScreen();

			String sql = String.format(
					"INSERT INTO book VALUES('%s', '%s', '%s', '%d', NULL);",
					id, name, author, year);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Book has been added successfully!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void delete_book(Statement stmt, Scanner scan) {
		try {
			System.out.print("\nEnter book ID : ");
			String id = scan.nextLine();

			clearScreen();

			String sql = String.format(
					"DELETE FROM book where book_id = '%s'", id);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Book has been deleted successfully!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void list_of_students(Statement stmt, Scanner scan) {
		String sql = "select * from student";
		ResultSet rs = executeSqlStmt(stmt, sql);

		try {
			System.out.println("List of students:\n");
			while (rs.next()) {
				String id = rs.getString("roll_number");
				String name = rs.getString("full_name");

				System.out.println("Roll number : " + id);
				System.out.println("Full Name: " + name);
				System.out.println("\n");
			}

			rs.close();
		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	static void list_of_librarians(Statement stmt, Scanner scan) {
		String sql = "select * from librarian";
		ResultSet rs = executeSqlStmt(stmt, sql);

		try {
			System.out.println("List of librarians:\n");
			while (rs.next()) {
				String id = rs.getString("librarian_id");
				String name = rs.getString("librarian_name");

				System.out.println("Roll number : " + id);
				System.out.println("Full Name: " + name);
				System.out.println("\n");
			}

			rs.close();
		} catch (SQLException e) {
			// e.printStackTrace();
		}
	}

	static void add_student(Statement stmt, Scanner scan) {
		try {
			System.out.print("Enter student roll number : ");
			String id = scan.nextLine();
			System.out.print("Enter student name : ");
			String name = scan.nextLine();

			clearScreen();

			String sql = String.format("INSERT INTO student VALUES('%s', '%s', NULL)", id, name);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Student has been added successfully!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void add_librarian(Statement stmt, Scanner scan) {
		try {
			System.out.print("Enter librarian id : ");
			String id = scan.nextLine();
			System.out.print("Enter librarian name : ");
			String name = scan.nextLine();
			System.out.print("Enter librarian password : ");
			String password = scan.nextLine();

			clearScreen();

			String sql = String.format("INSERT INTO librarian VALUES('%s', '%s', '%s')", id, name, password);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Librarian has been added successfully!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void delete_student(Statement stmt, Scanner scan) {
		try {
			System.out.print("Enter student roll number : ");
			String id = scan.nextLine();

			clearScreen();

			String sql = String.format("DELETE FROM student where roll_number = '%s'", id);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Student has been deleted successfully!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static void delete_librarian(Statement stmt, Scanner scan) {
		try {
			System.out.print("Enter librarian id : ");
			String id = scan.nextLine();

			clearScreen();

			String sql = String.format("DELETE FROM librarian where librarian_id = '%s'", id);
			int result = updateSqlStmt(stmt, sql);

			if (result != 0)
				System.out.println("Librarian has been deleted successfully!!\n");
			else
				System.out.println("Something went wrong!\n");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	static ResultSet executeSqlStmt(Statement stmt, String sql) {
		try {
			ResultSet rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException se) {
			// se.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	static int updateSqlStmt(Statement stmt, String sql) {
		try {
			int rs = stmt.executeUpdate(sql);
			return rs;
		} catch (SQLException se) {
			// se.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return 0;
	}

	static void clearScreen() {
		System.out.println("\033[H\033[J");
		System.out.flush();
	}
}
