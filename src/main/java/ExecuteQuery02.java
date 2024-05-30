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
//            System.out.println(rs2.getObject(1) + " , " + rs2.getObject(2)); OR

//            System.out.println(rs2.getString(1) + " , " + rs2.getInt(2));  // precise data type + column index OR
            System.out.println(rs1.getString("name") + " , " + rs1.getInt("grade"));  // precise data type + column names
        }

        System.out.println("========= HW TASK =========");
        // Print department name and grade of department which has the second-highest pass_grade
        // 1st way: Using SUB-QUERY
        System.out.println("First way:");
        String query2 = "SELECT department, pass_grade FROM departments WHERE pass_grade IN (SELECT MAX(pass_grade) AS second_highest_pass_grade FROM departments WHERE pass_grade < (SELECT MAX(pass_grade) FROM departments));";
        ResultSet rs2 = st.executeQuery(query2);
        while(rs2.next()){
            System.out.println(rs2.getString(1) + " , " +rs2.getInt(2));
        }

        // 2nd way: Using ORDER BY

        System.out.println("Second way:");

        String query3="SELECT department , pass_grade FROM departments ORDER BY  pass_grade DESC LIMIT 1 OFFSET 1";
        ResultSet rs3=st.executeQuery(query3);

        while(rs3.next()){
            System.out.println(rs3.getString(1)+": "+ rs3.getInt(2));
        }

        // Step 5: Close the statement and connection
        if (con != null){
            st.close();
            con.close();
            System.out.println("Connection closed");
        }



    }
}
