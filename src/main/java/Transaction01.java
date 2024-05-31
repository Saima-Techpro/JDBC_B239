import java.sql.*;

public class Transaction01 {

//    public Transaction01(){
//        // default constructor made visible
//    }

//    public Transaction01(String query){
//        // default constructor gets over-written when you create your own parameterised constructor
//        // If you want to keep default constructor as well, make it visible
//    }

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
        // Task 1: Delete students where department is 'Literature' (from students table)
        // normal query
//        String query1 = "DELETE FROM students WHERE department ILIKE 'Literature';";


        // parameterised query
        String query1 = "DELETE FROM students WHERE department ILIKE ? ;";  // Transaction
        PreparedStatement prs1 = con.prepareStatement(query1);

        prs1.setString(1, "Psychology");


        // by default, this is made available at the background when connection with database is established
        // it allows the transaction (SQL Queries) to commit automatically
        // con.setAutoCommit(true);
        // To stop auto-commit (shipping) of transactions, we need to EXPLICITLY declare this setAutoCommit() to FALSE
        con.setAutoCommit(false); // all transactions will be under our control from here onwards. (Just like ON/OFF Switch)

        // Let's suppose our transaction doesn't go ahead because of some system failure
        // In order to simulate / pretend our system failed, we create this if block which will act as if
        // system failed while running this class

        if (true){
           con.rollback();  // rollback() method is used to UNDO the transaction
        }

        // We allow the shipping /commit at this point by turning the switch ON =>  con.setAutoCommit(true);

        con.setAutoCommit(true);
        prs1.executeUpdate();

        // To see the data
        String query2 = "SELECT * FROM students";

        ResultSet rs = st.executeQuery(query2);

        while (rs.next()){
            System.out.println(rs.getInt("id") + " -- " +
                    rs.getString("name") + " -- " +
                    rs.getString("city") + " -- " +
                    rs.getString("grade") + " -- " +
                    rs.getString("department"));
        }


        // close the connection

        if (con !=null){
            rs.close();
            st.close();
            con.close();
            System.out.println("Connection is closed");
        } else {
            System.out.println("Connection is not closed");
        }





    }


}
