import java.sql.*;

public class Transaction02_NegativeScenario {
    public static void main(String[] args) throws Exception {

        //  Create the connection with DB
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");
        if (con != null){
            System.out.println("Connected!");
        }else {
            System.out.println("Not connected!");
        }

//        con.setAutoCommit(true); This is the code that works at the background once connection with the DB is established
        // It allows the queries (transactions) to be executed (committed/shipped) AUTOMATICALLY

        // Create Statement
        Statement st = con.createStatement();

        // Execute SQL query
        System.out.println("****** Task 1 ******");

        // Task 1: Transfer amount of 1000 from account_num:1234 to account_num:5678
        // normal query
        // For Fred
//        String query = "UPDATE accounts SET amount = amount-1000 WHERE account_num = 1234;";
        // For Barnie
//       String query = "UPDATE accounts SET amount = amount +1000 WHERE account_num = 5678;"

        // parameterised query

        String query = "UPDATE accounts SET amount = amount + ? WHERE account_num = ? ;";
        PreparedStatement prs = con.prepareStatement(query);

        // Set the values for Fred
        prs.setInt(1, -1000);
        prs.setInt(2, 1234);
        prs.executeUpdate();

        // suppose system fails
        if (true){
            throw new Exception();
        }


        // Set the values for Barnie
        prs.setInt(1, +1000);
        prs.setInt(2, 5678);
        prs.executeUpdate();


        // Barnie will never receive money because of system failure in the middle of Transaction
        // This is a NEGATIVE SCENARIO => both customers of this bank will NOT be happy



        // close the connection

        if (con !=null){
           prs.close();
            st.close();
            con.close();
            System.out.println("Connection is closed");
        } else {
            System.out.println("Connection is not closed");
        }




    }
}
