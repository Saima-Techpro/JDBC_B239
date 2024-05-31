import java.sql.*;

public class PreparedStatement01 {
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
        System.out.println("****** Task 1 ******");

        // Task 1: Update pass_grade to 475 of Mathematics department (use PreparedStatement)

        // normal SQL query
//        String query1 = "UPDATE departments SET pass_grade = 490 WHERE department = 'Literature';";
//        String query1 = "UPDATE departments SET pass_grade = 470 WHERE department = 'Psychology';";
//        String query1 = "UPDATE departments SET pass_grade = 475 WHERE department = 'Mathematics';";
//        String query1 = "UPDATE departments SET pass_grade = 475 WHERE department ILIKE 'Mathematics';";
//        String query1 = "UPDATE departments SET pass_grade = 475 WHERE department ILIKE 'mathematics';";

        // NOTE: ILIKE is used to ignore upper or lowercases for the data


        // dynamic query / parameterised query => to avoid repetition

        String query1 = "UPDATE departments SET pass_grade = ? WHERE department ILIKE ? ;";
        PreparedStatement prs1 = con.prepareStatement(query1);

        prs1.setInt(1, 475);
        prs1.setString(2, "mathematics");

        // Now use executeUpdate() to update the pass_grade column

        int numOfRowsUpdated = prs1.executeUpdate();
        System.out.println("numOfRowsUpdated = " + numOfRowsUpdated);  // 1

        // to see the data

        String query2 = "SELECT * FROM departments";

        ResultSet rs2 = st.executeQuery(query2);

        while (rs2.next()){
            System.out.println(rs2.getInt("dept_id") + " -- " +
                    rs2.getString("department") + " -- " +
                    rs2.getInt("pass_grade") + " -- " +
                    rs2.getString("campus"));
        }

        System.out.println("****** Task 2 ******");
        // Task 2: Update pass_grade to 480 of Literature department (use PreparedStatement)
        // We don't need to create another query for this update. We can use the existing PreparedStatement
        // and just set the new values
        prs1.setInt(1, 480);
        prs1.setString(2, "Literature");

        // now execute it
        prs1.executeUpdate();

        // to see the data

        String query3 = "SELECT * FROM departments";

        ResultSet rs3 = st.executeQuery(query3);

        while (rs3.next()){
            System.out.println(rs3.getInt("dept_id") + " -- " +
                    rs3.getString("department") + " -- " +
                    rs3.getInt("pass_grade") + " -- " +
                    rs3.getString("campus"));
        }

        System.out.println("****** Task 3 ******");
        // Task 3: Delete students where department is 'Mathematics' (from students table)
        // Can we use the existing Prepared Statement?
        // No  because 1) table is different  2) query itself is different (DELETE instead of UPDATE )

        // normal query
        // String query4 = "DELETE FROM students WHERE department ILIKE  'mathematics'";

        // parameterised query
        String query4 = "DELETE FROM students WHERE department ILIKE ? ";

        PreparedStatement prs = con.prepareStatement(query4);
        prs.setString(1, "mathematics");

        // now execute it
        prs.executeUpdate();

        // To see the data
        String query5 = "SELECT * FROM students";

        ResultSet rs5 = st.executeQuery(query5);

        while (rs5.next()){
            System.out.println(rs5.getInt("id") + " -- " +
                    rs5.getString("name") + " -- " +
                    rs5.getString("city") + " -- " +
                    rs5.getString("grade") + " -- " +
                    rs5.getString("department"));
        }


        System.out.println("****** Task 4 ******");

        // Task 4: Insert software engineering department using prepared statement into departments table.

        // normal query
        // String query6 = "INSERT INTO departments VALUES(5006, 'SoftWare Eng.', 530, 'North');";

        // parameterised query
        String query6 = "INSERT INTO departments VALUES(?, ?, ?, ?);";

        PreparedStatement preparedStatement = con.prepareStatement(query6);
        preparedStatement.setInt(1, 5006);
        preparedStatement.setInt(3, 530);
        preparedStatement.setString(4, "North");
        preparedStatement.setString(2, "Software Eng.");

        // now execute the preparedStatement
        preparedStatement.executeUpdate();

        // To see the data

        String query7 = "SELECT * FROM departments";

        ResultSet rs7 = st.executeQuery(query7);

        while (rs7.next()){
            System.out.println(rs7.getInt("dept_id") + " -- " +
                    rs7.getString("department") + " -- " +
                    rs7.getInt("pass_grade") + " -- " +
                    rs7.getString("campus"));
        }


        // Close the connection

        if (con != null){
            prs1.close();
            prs.close();
            preparedStatement.close();
            st.close();
            con.close();
        }else {
            System.out.println("Still connected!!");
        }


    }
}
