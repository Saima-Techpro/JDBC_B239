import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtils {

    public static Connection connection;  // This is class variable now. Its scope is wider.
    public static Statement statement; // This is class variable
    public static PreparedStatement prs;
    public static ResultSet rs;



//    Step 1: Register the Driver class  (OPTIONAL)
//    Step 2: Create connection with the DataBase

    // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");

    public static Connection connectToDataBase(){
        //Connection connection = null;  // this is still local variable. Its scope is so limited.
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");
            System.out.println("Connection is successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

//    Step 3: Create a statement
    public static Statement createStatement(){
        try {
            statement = connection.createStatement();
            System.out.println("Statement created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }


//    Step 4: Execute the query

    // Create Reusable method to execute query

    public static boolean execute(String query){
        boolean result; // local variable
        try {
            result = statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    // To insert values into a table, we have reusable methods
    // 1. preparedStatement()
    //2. executeUpdateWithPreSt()
    public static PreparedStatement preparedStatement(String query){
        //String query1 = "INSERT INTO employees VALUES (?, ?, ?);";  // parameterised query
        PreparedStatement prs = null;
        try {
            prs = JDBCUtils.connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prs.setInt(1, 102);
            prs.setString(2, "Tanya");
            prs.setInt(3, 100000);
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prs;
    }

    // executeUpdateWithPreSt()
    public static int executeUpdateWithPreSt(String query){
        int rowsUpdated = 0;
        try {
            prs =  connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prs.setInt(1, 103);
            prs.setString(2, "Keira");
            prs.setInt(3, 70000);
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsUpdated;
    }

    // Method to get the data from a given column and return as a list

    public static List<Object> getColumnData(String columnName , String tableName){

        List<Object> list = new ArrayList<>(); // to store the data that we extract from DB

        String query = "SELECT "+columnName+" FROM "+tableName;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (true){
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                list.add(rs.getObject(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return list;

    }

























//    Step 5: Close the connection
    public static  void closeStatementAndConnection(){
        try {
            statement.close();
            connection.close();
            System.out.println("Connection closed successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }







}
