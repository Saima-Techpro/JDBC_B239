import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedunnaTest {

    /*
     Given
          User connects to the database
        When
          User sends the query to get room_id for the given room_number from "room" table
        Then
          Verify that the room_id is unique
        And
          User closes the connection
     */

    @Test
    public void medunnaTest() throws SQLException {

//        User connects to the database
        JDBCUtils.connectToDataBase("medunna.com", "medunna_db_v2", "select_user", "Medunna_pass_@6");

//        User sends the query to get room id for the given room_number from "room" table
         String query = "SELECT * FROM room WHERE room_number = ? ";
         PreparedStatement prs = JDBCUtils.connection.prepareStatement(query);
         prs.setInt(1, 75809);

         ResultSet rs = prs.executeQuery();
         List<Integer> list = new ArrayList<>();
        while (rs.next()){
             list.add(rs.getInt(1));
         }
        System.out.println("room id = " + list);  // [113625]

        //JDBCUtils.getColumnData("id","room");
// String query = "SELECT room_id FROM room;";
// ResultSet rs = JDBCUtils.statement.executeQuery(query);
//  Set<Integer> id= new HashSet<>();
//  while (rs.next()){
//      id.add(rs.getInt(1));
//   }
//     System.out.println("room_id: "+ id);
//    }

        // If we use JDBCUtils.getColumnData() for the above query, it will show error because this method works with normal query.
        // Whereas we are dealing with parameterised query in this test. So we can't use this Reusable method.
        // System.out.println(JDBCUtils.getColumnData("room_number", "room"));


        /*
        This Reusable method will not work either. Because it works with a parameterised query which has three values (?, ? ,?)
        But our current query has only one value ?
        So either we need to create another method which works with the above query.
        Or change all details of existing Reusable method.
        Better option: create prepare Statement, set values and execute in the test class.

        System.out.println(JDBCUtils.executeUpdateWithPreSt(query));

         */

//        Verify that the room id is unique

        int counter = 0;
        for (Integer w: list) {
            if (w > 1) {
                counter++;
            }
        }
        System.out.println(counter );

        Assertions.assertEquals( 1, counter, "room id is not unique");

//        User closes the connection
        prs.close();
        JDBCUtils.connection.close();


    }
}
