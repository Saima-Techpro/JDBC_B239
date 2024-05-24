import java.sql.*;

public class ExecuteQuery02 {
    public static void main(String[] args) throws SQLException {

        // Step 1: Register with Driver class (OPTIONAL)

        // Step 2: Create connection with DB
        // port: 5433
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");


        if (con != null){
            System.out.println("Successfully connected.");
        } else{
            System.out.println("Not connected!");
        }

        // Step 3: Create statement
        Statement st = con.createStatement();

        // Step 4: Execute SQL queries
        System.out.println("=========== Task 1 ===========");
        // Create students and departments tables using pgAdmin4
        // Task 1: Display names of the students and their grades if their grades are higher than the pass grade of their department

        String query1 = "SELECT name, grade FROM students s INNER JOIN departments d ON s.department = d.department WHERE s.grade > d.pass_grade";

        ResultSet rs1 = st.executeQuery(query1);

        while (rs1.next()){
//            System.out.println(rs1.getObject(1) + " , " + rs1.getObject(2)); OR

//            System.out.println(rs1.getString(1) + " , " + rs1.getInt(2));  // precise data type + column index OR
            System.out.println(rs1.getString("name") + " , " + rs1.getInt("grade"));  // precise data type + column names
        }

        System.out.println("========= HW TASK =========");
        // Print department name and grade of department which has the second-highest pass_grade
        // 1st way: Using SUB-QUERY
        // 2nd way: Using ORDER BY





        // Step 5: Close the statement and connection
        if (con != null){
            st.close();
            con.close();
            System.out.println("Connection closed");
        }



    }
}
