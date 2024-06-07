import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws SQLException {

//        Step 1: Register the Driver class  (OPTIONAL)
//        Step 2: Create connection with the DataBase
        // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");

        JDBCUtils.connectToDataBase("localhost","jdbc_b239","b239_user", "password");
//        Step 3: Create a statement
        JDBCUtils.createStatement();
//        Step 4: Execute the query
        // Task: Create students table in the database
        String query = "CREATE TABLE IF NOT EXISTS employees (employee_id INT, employee_name VARCHAR (30), salary INT);";
        System.out.println(JDBCUtils.execute(query));  // false => the table is created, but no resultSet is produced

        // Insert some values in this table (Use Prepared Statement)
        String query1 = "INSERT INTO employees VALUES (?, ?, ?);";  // parameterised query

        // There are 3 ways we can perform this task:

        // 1st way: Inside the test/same class (My personal choice)
        // Call JDBCUtils.connection to create prepareStatement and set the values in the same class
        // use executeUpdate() method to insert those value


//        PreparedStatement prs = JDBCUtils.connection.prepareStatement(query1);
//
//        // set the values
//        prs.setInt(1, 101);
//        prs.setString(2, "Anna");
//        prs.setInt(3, 90000);
//        // execute
//        prs.executeUpdate();

        // 2nd way: Use the reusable method preparedStatement() from JDBCUtils class
        JDBCUtils.preparedStatement(query1);

        // 3rd way:
        // Use the reusable method executeUpdateWithPreSt() from JDBCUtils class
        JDBCUtils.executeUpdateWithPreSt(query1);


        // Get the column data
        System.out.println(JDBCUtils.getColumnData("employee_id", "employees"));
        System.out.println(JDBCUtils.getColumnData("employee_name", "employees"));
        System.out.println(JDBCUtils.getColumnData("name", "students"));


//        Step 5: Close the connection
        JDBCUtils.closeStatementAndConnection();


    }
}
