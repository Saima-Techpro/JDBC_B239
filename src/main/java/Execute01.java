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