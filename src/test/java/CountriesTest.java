import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountriesTest {

    // This test case is written in GHERKIN Language

    /*
        Given (pre-condition)
          User connects to the database
        When (action)
          User sends the query to get the "phone code" from "countries" table
        Then (verification)
          Verify that the number of "phone code" greater than 300 is 13.
        And (last step)
          User closes the connection

     */

    @Test
    public void countriesTest() throws SQLException {

//        User connects to the database
        JDBCUtils.connectToDataBase("localhost","jdbc_b239","b239_user", "password");
        JDBCUtils.createStatement();

//        User sends the query to get the "phone_code" from "countries" table
        // 1st way:
        String query = "SELECT phone_code FROM countries;";
        ResultSet rs = JDBCUtils.statement.executeQuery(query);

        List<Integer> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getInt(1));
        }
        System.out.println("list = " + list);
        System.out.println("list size= " + list.size());

        System.out.println("======== Reusable Method ==========");
       // 2nd way: using Reusable Method from  JDBCUtils
        System.out.println(JDBCUtils.getColumnData("phone_code", "countries"));


//        Verify that the number of "phone code" greater than 300 is 13.

        int counter = 0;
        for (Integer w: list) {
            if (w > 300) {
                counter++;
            }
        }
        System.out.println("number of phone code greater than 300: " + counter );

        Assertions.assertEquals( 13, counter, "result is not the same");

//        User closes the connection
        JDBCUtils.closeStatementAndConnection();


    }
}
