/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQLite;

//import org.sqlite.JDBC;
import java.sql.ResultSet;

import coursescheduleapp.model.CourseDatabase;

/**
 *
 * @author cboyle1
 */
public class SQlite {
	public static void main(String[] args) throws Exception {
		CourseDatabase db=new CourseDatabase();
		
        ResultSet rs = db.stat.executeQuery("select * from classes;");
        while (rs.next()) {
          System.out.println("name = " + rs.getString("name"));
          //System.out.println("job = " + rs.getString("occupation"));
        }
        rs.close();
		
		
	  /*
    Class.forName("org.sqlite.JDBC");
    Connection conn =
      DriverManager.getConnection("jdbc:sqlite:test.db");
    Statement stat = conn.createStatement();
    stat.executeUpdate("drop table if exists people;");
    stat.executeUpdate("create table people (name, occupation);");
    PreparedStatement prep = conn.prepareStatement(
      "insert into people values (?, ?);");

    prep.setString(1, "Gandhi");
    prep.setString(2, "politics");
    prep.addBatch();
    prep.setString(1, "Turing");
    prep.setString(2, "computers");
    prep.addBatch();
    prep.setString(1, "Wittgenstein");
    prep.setString(2, "smartypants");
    prep.addBatch();

    conn.setAutoCommit(false);
    prep.executeBatch();
    conn.setAutoCommit(true);

    ResultSet rs = stat.executeQuery("select * from people;");
    while (rs.next()) {
      System.out.println("name = " + rs.getString("name"));
      System.out.println("job = " + rs.getString("occupation"));
    }
    rs.close();
    conn.close();*/
  }
}
