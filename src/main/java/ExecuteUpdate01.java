import java.sql.*;

public class ExecuteUpdate01 {
    public static void main(String[] args) throws SQLException {

       //  Create the connection with DB
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");
        if (con != null){
            System.out.println("Connected!");
        }else {
            System.out.println("Not connected!");
        }

       // Create Statement
        Statement st = con.createStatement();

       // Execute SQL query
        // Task 1: -- Update salaries of developers whose salaries are less than average salary with average salary
        String query1 = "UPDATE developers SET salary = (SELECT AVG(salary) FROM developers) WHERE salary< (SELECT AVG(salary) FROM developers);" ;

        // executeUpdate() method returns the number of rows that were updated as a result of that query execution
//        int numOfRowsUpdated = st.executeUpdate(query1);
//        System.out.println("numOfRowsUpdated = " + numOfRowsUpdated);  // 15

        // To see the updated data, we ALWAYS use executeQuery() method
        String query2 = "SELECT id, name, salary, prog_lang FROM developers";
        ResultSet rs2 = st.executeQuery(query2);

        while (rs2.next()){
            System.out.println(rs2.getInt("id") + " -- " +
                    rs2.getString("name") + " -- " +
                    rs2.getDouble("salary") + " -- " +
                    rs2.getString("prog_lang"));
        }

        // Task 2: Add a new developer to the developers table (id, name, salary, prog_lang)

        String query3 = "INSERT INTO developers VALUES (22, 'Keira Knightly',15000, 'Java')";
//        int rowsUpdated = st.executeUpdate(query3);
//        System.out.println("rowsUpdated = " + rowsUpdated); // 1

        // To see the data
        String query4 = "SELECT * FROM developers";

        ResultSet rs4 = st.executeQuery(query4);

        while (rs4.next()){
            int id = rs4.getInt("id");
            System.out.println("id = " + id);
            String name = rs4.getString("name");
            System.out.println("name = " + name);
            double salary = rs4.getDouble("salary");
            System.out.println("salary = " + salary);
            String prog_lang = rs4.getString("prog_lang");
            System.out.println("prog_lang = " + prog_lang);
        }

        // Task 3: Delete the rows from the developers table if prog_lang is 'Ruby'
        String query5 = "DELETE FROM developers WHERE prog_lang='Ruby';";
        System.out.println("delete query = " + st.executeUpdate(query5));  // 4

        // To see the data
        String query6 = "SELECT * FROM developers";
        ResultSet rs6 = st.executeQuery(query6);

        while (rs6.next()){
            System.out.println(rs6.getInt("id") + " -- " +
                    rs6.getString("name") + " -- " +
                    rs6.getDouble("salary") + " -- " +
                    rs6.getString("prog_lang"));
        }

        // Close the connection

        if (con != null){
            st.close();
            con.close();
        }else {
            System.out.println("Still connected!!");
        }



    }
}
