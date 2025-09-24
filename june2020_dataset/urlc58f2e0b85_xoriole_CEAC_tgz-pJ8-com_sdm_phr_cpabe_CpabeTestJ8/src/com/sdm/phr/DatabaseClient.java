package com.sdm.phr;

import com.sdm.phr.cpabe.Cpabe;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class DatabaseClient {

    private static DatabaseClient instance = new DatabaseClient();

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/phr";

    //  Database credentials
    static final String USER = "phr";
    static final String PASS = "phr";

    Connection con = null;
    Statement stmt = null;

    boolean setupError = false;

    private DatabaseClient() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            setupError = true;
        }
    }

    public static DatabaseClient getInstance() {
        return instance;
    }

    public void exit() {
        try {
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addPatient(String fullName, String dob, String address, String readMasterKeyChecksum, String writeMasterKeyChecksum) {
        String sql = "insert into patient(full_name, date_of_birth, address, read_master_key_checksum, write_master_key_checksum) "
                + "values('" + fullName + "','" + dob + "','" + address + "','" + readMasterKeyChecksum + "','" + writeMasterKeyChecksum + "');";
        System.out.println("sql:" + sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validatePatientLogin(String fullName, String readMasterKeyChecksum, String writeMasterKeyChecksum) {
        try {
            String sql = "select read_master_key_checksum, write_master_key_checksum from patient where full_name='" + fullName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String dbReadMasterKeyChecksum = rs.getString("read_master_key_checksum");
                if (!readMasterKeyChecksum.equals(dbReadMasterKeyChecksum)) {
                    return false;
                }
                String dbWriteMasterKeyChecksum = rs.getString("write_master_key_checksum");
                if (!writeMasterKeyChecksum.equals(dbWriteMasterKeyChecksum)) {
                    return false;
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Map<String, Integer> getOrgnMap() {
        Map<String, Integer> orgnMap = new HashMap<String, Integer>();
        String sql = "select oid, name from organization";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int oid = rs.getInt("oid");
                String name = rs.getString("name");
                orgnMap.put(name, oid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgnMap;
    }

    public void addUser(String fullName, int orgnId, String attributes, String readSecretKeyChecksum, String writeSecretKeyChecksum) {
        String sql = "insert into user(full_name, orgn_id, attributes, read_secret_key_checksum,write_secret_key_checksum) "
                + "values('" + fullName + "','" + orgnId + "','" + attributes + "','" + readSecretKeyChecksum + "','" + writeSecretKeyChecksum + "');";
        System.out.println("sql:" + sql);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validateUserLogin(String fullName, int orgnId, String readSecretKeyChecksum, String writeSecretKeyChecksum) {
        try {
            String sql = "select uid, read_secret_key_checksum, write_secret_key_checksum from user where full_name='" + fullName + "' and orgn_id=" + orgnId;
//            System.out.println("sql:"+sql);
//            System.out.println("read secret key checksum:"+readSecretKeyChecksum);
//            System.out.println("write secret key checksum:"+writeSecretKeyChecksum);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String dbReadSecretKeyChecksum = rs.getString("read_secret_key_checksum");
                String dbWriteSecretKeyChecksum = rs.getString("write_secret_key_checksum");
//                System.out.println("db read secret key checksum:"+dbReadSecretKeyChecksum);
//                System.out.println("db write secret key checksum:"+dbWriteSecretKeyChecksum);
                if (!readSecretKeyChecksum.equals(dbReadSecretKeyChecksum)) {
                    return false;
                }
                if (!writeSecretKeyChecksum.equals(dbWriteSecretKeyChecksum)) {
                    return false;
                }
                Session.getInstance().setUserId(rs.getInt("uid"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Map<String, Integer> getPatientMap() {
        Map<String, Integer> patientMap = new HashMap<String, Integer>();
        String sql = "select full_name, pid from patient";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int pid = rs.getInt("pid");
                String name = rs.getString("full_name");
                patientMap.put(name, pid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patientMap;
    }

    public int getPatientUserId(String fullName) {
        String sql = "select uid from user where full_name='" + fullName + "'";
        System.out.println("getting patient uid:" + sql);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt("uid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getPid(String fullName) {
        String sql = "select pid from patient where full_name='" + fullName + "'";
        System.out.println("getting patient uid:" + sql);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt("pid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
//    public void insertSection(int patientID, CipherKeyPair ckp, String policy) {
//        String SQLstatement = "INSERT INTO health_data VALUES('" + patientID + "', "
//                + "SELECT COUNT(*) FROM health_data, '" + ckp.getCiphertext() + "', '" + policy + "', '" + ckp.getKey() + "');";
////Send statement to database.
//    }

    public boolean insertSection(int patientID, int authorID, CipherKeyPair ckp,
            String policy) {
        System.out.println("insertSection");
        String query = "insert into health_data (pid, author_id, aes_key, "
                + "cipher_text, access_policy) values (" + patientID + ", "
                + authorID + ", '" + ckp.getKey() + "', '" + ckp.getCiphertext()
                + "', '" + policy + "')";
        System.out.println("query: " + query);
        //Send statement to database.
        try {
            stmt.executeUpdate(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertSection(int patientID, int authorID, CipherKeyPair ckp,
            String policy, String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("token null or empty");
            return false;
        }
        if (!isValidToken(patientID, authorID, token)) {
            System.out.println("Token invalid. Exiting");
            return false;
        }

        System.out.println("insertSection");
        String query = "insert into health_data (pid, author_id, aes_key, "
                + "cipher_text, access_policy) values (" + patientID + ", "
                + authorID + ", '" + ckp.getKey() + "', '" + ckp.getCiphertext()
                + "', '" + policy + "')";
        System.out.println("query: " + query);
        //Send statement to database.
        try {
            stmt.executeUpdate(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CipherKeyPair retrieveSection(String patientID, String attribute) {
        String SQLstatement = "SELECT contents FROM health_data WHERE patientID='" + patientID + "' AND policy LIKE %" + attribute + "%;";
        //Send statement to database.

        //CipherKeyPair result = new CipherKeyPair("", "");
        return null;
    }

    public DefaultTableModel retrieveSection() {

        DefaultTableModel dtm = new DefaultTableModel();
//        String sql = "select sid,pid, author_id, access_policy from health_data, user,patient where user.uid=author_id and pid=patient.pid";
        String sql = "select sid,patient.full_name as patient, user.full_name as author, access_policy from health_data, user,patient where user.uid=author_id and health_data.pid=patient.pid";
        try {
            ResultSet res = stmt.executeQuery(sql);
            int count = 0;
            while (res.next()) {
                ++count;
                // Get data from the current row and use it
            }
            res.first();

            res = stmt.executeQuery(sql);
            Object[] cols = {"ID", "Patient", "Author", "Policy"};
            Object[][] data = new Object[count][];
            ResultSetMetaData meta = res.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            int j = 0;
            while (res.next()) {
                Object[] rowData = new Object[numberOfColumns];
                for (int i = 0; i < rowData.length; ++i) {
                    rowData[i] = res.getObject(i + 1);
                }
                data[j++] = rowData;
            }
            dtm = new DefaultTableModel(data, cols);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtm;
    }

    public CipherKeyPair getEncryptedRecordPair(int sid) {
        CipherKeyPair ckp = null;
        String sql = "select cipher_text, aes_key, access_policy from health_data where sid=" + sid;
        System.out.println("sql:" + sql);
        try {
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                byte[] cipherText = Base64.decode(res.getString("cipher_text"));
                byte[] aesKey = Base64.decode(res.getString("aes_key"));
                ckp = new CipherKeyPair(cipherText, aesKey);
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ckp;
    }

    public String getOrgName(int oid) throws SQLException {
        String orgQuery = "select name from organization where oid = " + oid;

        //Send statement to database
        ResultSet rs = stmt.executeQuery(orgQuery);
        while (rs.next()) {
            return rs.getString("name");
        }
        return null;
    }

    /**
     *
     * @param oid Organization that wants to write
     * @param pid Patient id where the organization wants to write
     * @param ptToken Plaintext token
     * @return
     */
    public boolean insertTokenSession(int oid, int pid, String ptToken) {
        //Delete previous tokens
        String deleteQuery = "delete from token_session where requester_id = "
                + oid + " and p_id = " + pid;
        //Insert new token
        String storeQuery = "insert into token_session (requester_id, p_id, "
                + "token) values (" + oid + "," + pid + ","
                + "md5(" + ptToken + "))";
        //Send statement to database
        try {
            stmt.executeUpdate(deleteQuery);
        } catch (Exception e) {
            //Try to delete previous tokens.
            //No problem if there are no old tokens.
        }
        try {
            stmt.executeUpdate(storeQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isValidToken(int pid, int authorOid, String ptToken) {

        String query = "select * from token_session where p_id=" + pid
                + " and requester_id=" + authorOid + " and token = "
                + "md5(" + ptToken + ")";
        try {
            //Send statement to database and save here the answer
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean insertBlob(byte[] binaryData) {
        String b64 = Base64.encode(binaryData);
        System.out.println(b64);
        String query = "insert into test values ('"
                + b64 + "')";

        try {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            //Try to delete previous tokens.
            //No problem if there are no old tokens.
        }

        return true;
    }

    public String readBlob() throws SQLException {
        String query = "select bincol from test";
        String b64 = null;

        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            b64 = rs.getString("bincol");
            System.out.println(b64);
        }

        return b64;
    }

    public String getUserAttributes(int uid) {
        String sql = "select attributes from user where uid = " + uid;
        try {
            //Send statement to database
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                return rs.getString("attributes");
            }
        } catch (SQLException sqle) {
            System.out.println("Exception in getUserAttribute");
            sqle.printStackTrace();
        }
        return null;
    }

    public String generateToken(int pid, int uid) {
        String userAttribute = getUserAttributes(uid);
        String policy = userAttribute;
        int countAttr = userAttribute.split(" ").length;
        if (countAttr > 1) {
            policy = userAttribute + " 1of" + userAttribute.split(" ").length;
        }
        System.out.println("policy:" + policy);
        String token = (new Random(System.currentTimeMillis())).nextLong() + "";
        insertTokenSession(uid, pid, token);
        Cpabe cpabe = new Cpabe();
        try {
            return Base64.encode(cpabe.enc(KeyConfig.getInstance().getPatientWritePublicKeyPath(), policy, token));
        } catch (Exception ex) {
            Logger.getLogger(DatabaseClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
