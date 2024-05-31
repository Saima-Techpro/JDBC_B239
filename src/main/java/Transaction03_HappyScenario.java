import java.sql.*;

public class Transaction03_HappyScenario {

    public static void main(String[] args) throws Exception {
        //  Create the connection with DB
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_b239", "b239_user", "password");
        con.setAutoCommit(true); // This is the code that works at the background once connection with the DB is established. It's invisible by default

        // Create Statement
        Statement st = con.createStatement();

        // WE take the control and STOP auto-commit (shipping)
        con.setAutoCommit(false);  // we switched OFF the auto-shipping

        // Execute SQL query
        System.out.println("****** Task 1 ******");
        // Task 1: Transfer amount of 1000 from account_num:1234 to account_num:5678
        // parameterised query

        String query = "UPDATE accounts SET amount = amount + ? WHERE account_num = ? ;";
        PreparedStatement prs = con.prepareStatement(query);

        Savepoint savepoint = null;


        try{

            savepoint = con.setSavepoint(); // returning point in case rollBack() ever works

            // Set the values for Fred
            prs.setInt(1, -1000);
            prs.setInt(2, 1234);
            prs.executeUpdate();

//            // suppose system fails
//            if (true){
//                throw new Exception();
//            }

            // suppose system doesn't fail
            if (false){
                throw new Exception();
            }
            // Set the values for Barnie
            prs.setInt(1, +1000);
            prs.setInt(2, 5678);
            prs.executeUpdate();

            con.setAutoCommit(true); // we are allowing the auto-ship to happen at this point
            System.out.println("Transaction is successful!!");


           System.out.println("****** To See the data ******");
            // To see the data
            String query1 = "SELECT * FROM accounts";

            ResultSet rs = st.executeQuery(query1);

            while (rs.next()){
                System.out.println(rs.getInt("account_num") + " -- " +
                        rs.getString("name") + " -- " +
                        rs.getInt("amount"));
            }


            con.close();

        }catch (Exception e){
            con.rollback();  // undo the transaction
            System.out.println("Transaction didn't go ahead. ");
            con.close();

        }


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
