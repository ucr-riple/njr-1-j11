package ccproxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    Connection conn = null;

    public DBConnection(String dbHost, String dbPort, String dbName, String dbUser, String dbPass) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            System.exit(1);
        }

        try {
            String connString = new String("dbc:postgresql://" + dbHost + ":" + dbPort + "/"
                    + dbName);
            conn = DriverManager.getConnection(connString, dbUser, dbPass);
        } catch (SQLException se) {
            se.printStackTrace();
            System.exit(1);
        }

    }

    synchronized public String quickQuery(String q) {
        String res = null;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                res = new String(rs.getString(1));
                break;
            }
            rs.close();
            st.close();

            return res;
        } catch (SQLException se) {
            se.printStackTrace();
            System.exit(1);
        }
        return "";
    }

    synchronized public ResultSet query(String q) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(q);
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

}