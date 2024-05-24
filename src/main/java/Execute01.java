import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*
        NOTE:
        pgAdmin4 is used for MANUAL Testing of Database.
        JDBC Driver is used for AUTOMATION Testing of Database.

        To create connection with DB, follow these steps:
        Step 1: Register the Driver class  (OPTIONAL)
        Step 2: Create connection with the DataBase
        Step 3: Create a statement
        Step 4: Execute the query
        Step 5: Close the connection

         */

        // Step 1: Register the Driver class
        Class.forName("org.postgresql.Driver"); // OPTIONAL since JAVA 7

        // Step 2: Create connection with the DataBase
        // port= 5432 OR 5433
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");

        if (connection != null) {
            System.out.println("Connection is successful");
        } else {
            System.out.println("Connection is not successful");
        }

        // Step 3: Create a statement
        Statement statement = connection.createStatement();

        // Step 4: Execute the query
        boolean sql1 = statement.execute("CREATE TABLE IF NOT EXISTS employees (employee_id INT, employee_name VARCHAR (30), salary INT);");
        System.out.println("sql1 = " + sql1);  // false because execute() is used here with DDL query


        /*
        NOTES about execute() method
        1. execute() method returns boolean
        2. It can be used with DDL(create, alter, drop table), DQL (reading the data using SELECT), DML (add, update data)
        3. execute() method with ddl and dml => always returns FALSE
        4. execute() method with dql => can return TRUE or FALSE because it checks whether ResultSet is created or not.
                                         With SELECT statement,  ResultSet will be created every time, so it will return true
                                         indicating that rows exist (even if they're empty at the moment)

            SUMMARY:

             The execute() method is designed to handle any type of SQL statement, not just queries.
             Its versatility is one of its strengths.When it returns true, it signals that the SQL statement executed
             was a query that generated a ResultSet.
             If the execute() method were to return false, it would mean that the statement executed was an
             update, insert, delete, or a DDL statement, which does not produce a ResultSet.

         */
        // Add a column to the employees table
        String query = "ALTER TABLE employees ADD COLUMN IF NOT EXISTS employee_address VARCHAR (50);";
        boolean sql2 = statement.execute(query);
        System.out.println("sql2 = " + sql2);  // false because execute() is used here with DDL query

        // Read the data
        System.out.println("table without data: " + statement.execute("SELECT * FROM employees;")); // true

        // Add a record to a table employees

        boolean sql3 = statement.execute("INSERT INTO employees VALUES (01, 'John Doe', 5000, 'Texas, US');"); // DML
        System.out.println("sql3 = " + sql3);  // false because execute() is used here with DML query

        // Read the record
        boolean sql4 = statement.execute("SELECT * FROM employees;"); // DQL
        System.out.println("sql4 = " + sql4);  // true

        // Drop the table
        System.out.println(statement.execute("DROP TABLE employees;"));  // false because execute() is used here with DDL query

        // Step 5: Close the connection
        if (connection != null) {
            statement.close();
            connection.close();
            System.out.println("Connection closed successfully!");
        } else {
            System.out.println("Connection is not closed.");
        }
    }
}


/*

chatGPT Notes about execute() method:

In JDBC (Java Database Connectivity), the execute() method can be used to execute any SQL statement.
This method is designed to handle different types of SQL queries, including DDL (Data Definition Language),
DML (Data Manipulation Language), and DQL (Data Query Language) statements. When dealing with DQL, specifically,
the execute() method returns true for a couple of reasons:

Result Set Indication: The primary reason is to indicate that the execution of the SQL statement has produced a ResultSet.
DQL statements, such as SELECT queries, are expected to return data from the database. When execute() is called and
a SELECT statement is executed, a ResultSet object is generated, which contains the data retrieved by the query.
The method returning true signifies that this ResultSet is available and can be processed.

Versatility of execute() Method: The execute() method is designed to handle any type of SQL statement, not just queries.
 Its versatility is one of its strengths. When it returns true, it signals that the SQL statement executed was a query
 that generated a ResultSet. If the execute() method were to return false, it would mean that the statement executed
 was an update, insert, delete, or a DDL statement, which does not produce a ResultSet.

Here’s a brief overview of how the execute() method works in the context of different types of SQL statements:

SELECT Statements (DQL): These statements are meant to retrieve data from the database. When such a statement is
executed, execute() returns true to indicate that a ResultSet has been produced.

INSERT, UPDATE, DELETE Statements (DML): These statements modify the data in the database but do not
produce a ResultSet. When such a statement is executed, execute() returns false.

DDL Statements: These statements alter the schema of the database (e.g., CREATE TABLE, ALTER TABLE).
These also do not produce a ResultSet, so execute() returns false.
 */