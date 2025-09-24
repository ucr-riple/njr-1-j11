package nextgen.importer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nextgen.model.Attribute;
import nextgen.model.Element;
import nextgen.model.Entity;
import nextgen.model.Key;
import nextgen.model.Project;
import nextgen.model.enums.Cardinality;
import nextgen.model.enums.KeyType;

/**
 *
 * @author Rodrigo
 */
public class MysqlImporter {

    public static void importer(Project project, String hostname, String user, String password, int port, String database) throws SQLException {
        try (Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, user, password)) {
            Statement statement = (Statement) con.createStatement();
            try (ResultSet result = statement.executeQuery("SHOW tables")) {
                while (result.next()) {
                    String entity = result.getString(1);
                    Statement statement1 = (Statement) con.createStatement();
                    try (ResultSet result2 = statement1.executeQuery("SHOW columns FROM " + entity)) {
                        Element e = new Element(capitalize(entity), "", entity);
                        int i = 1;
                        Key primary = new Key("Primary", KeyType.Primary);
                        while (result2.next()) {
                            String field = result2.getString(1);
                            String type = result2.getString(2);
                            String keyType = result2.getString(4);
                            Attribute attribute = new Attribute(i, field, getEntity(type), Cardinality.Single, true, "", "", false, "");
                            i++;
                            if (keyType.equals("PRI")) {
                                primary.getAttributes().add(attribute);
                            }
                            e.getAttributes().add(attribute);
                        }
                        e.getKeys().add(primary);
                        project.getElements().add(e);
                    }
                }
            }
        }
    }

    private static Entity getEntity(String type) {
        if (type.matches("int.*")) {
            return new Entity("Integer", "");
        } else if (type.matches("double")) {
            return new Entity("Double", "");
        } else if (type.matches("float")) {
            return new Entity("Float", "");
        } else if (type.matches("smallint.*")) {
            return new Entity("Integer", "");
        } else {
            return new Entity("String", "");
        }
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
