import java.sql.*;

public class ExecuteQuery01 {

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
        // Task 1: Get the country_name from countries table where id is between 3 and 9

        String query1 = "SELECT country_name FROM countries WHERE id BETWEEN 3 AND 9;";
        ResultSet rs1  = st.executeQuery(query1);
//        System.out.println("rs1 = " + rs1);  //  returns reference => org.postgresql.jdbc.PgResultSet@694e1548
//
//        System.out.println(rs1.next()); // true
//        System.out.println(rs1.getString("country_name")); // Algeria
//
//        System.out.println(rs1.next()); // true
//        System.out.println(rs1.getString("country_name")); //  Argentina
//        rs1.next();
//        System.out.println(rs1.getString("country_name")); // Australia

        // In order to avoid repetition, we put rs1.next() in a loop

        System.out.println("==========================");
        while (rs1.next()){
            System.out.println(rs1.getString("country_name"));
        }

        System.out.println("=========== Task 2 ===========");

        // Task 2: Get phone_code and country_name from the countries table where code is greater than 500
        String query2 = "SELECT phone_code, country_name FROM countries WHERE phone_code > 500;";

        ResultSet rs2 = st.executeQuery(query2);

        while (rs2.next()){
//            System.out.println(rs2.getInt("phone_code") +" , " + (rs2.getString("country_name")));
            System.out.println(rs2.getInt(1) +" , " + (rs2.getString(2)));
        }

        System.out.println("=========== Task 3 ===========");
        // Create developers table though pgAdmin4

        // Task 3: Get all information about the developers whose salary is the lowest

        String query3 = "SELECT * FROM developers WHERE salary = (SELECT MIN(salary) FROM developers);";

        // System.out.println("query3 = " + st.execute(query3));  // true because it confirms that resultset is being created

        ResultSet rs3 = st.executeQuery(query3);

        System.out.println("rs3 = " + rs3); // org.postgresql.jdbc.PgResultSet@711f39f9

//        while (rs3.next()){
//            System.out.println(rs3.getInt("id") + " -- " + rs3.getString("name")
//                    + " -- " + rs3.getInt("salary") + " -- " + rs3.getString("prog_lang"));
//        }

        System.out.println("======== with index =======");
        while (rs3.next()){
            System.out.println(rs3.getInt(1) + " -- " + rs3.getString(2)
                    + " -- " + rs3.getInt(3) + " -- " + rs3.getString(4));
        }

        // Step 5: Close the statement and connection
        if (con != null){
            st.close();
            con.close();
            System.out.println("Connection closed");
        }


    }
}
