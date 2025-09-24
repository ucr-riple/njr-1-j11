package ru.spbau.tinydb.bufferManager;

import ru.spbau.tinydb.btree.BxTree;
import ru.spbau.tinydb.queries.Attribute;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.table.Record;
import ru.spbau.tinydb.table.Table;
import ru.spbau.tinydb.table.TableBase;
import ru.spbau.tinydb.table.ViewWithId;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sergey on 09.10.2014.
 */
public class TableTest {

    private static final int COUNT = 1000000;
    private static final int RECORD_SIZE = 49;

    public static void main(String[] args) throws IOException, ExecutionException {
        testBaseTable();
        testTable();
    }

    private static void testTable() throws FileNotFoundException, ExecutionException, UnsupportedEncodingException {
        new File("db_test").delete();
        BufferManager bufferManager = new BufferManager("db_test");
        Collection<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("Test int attr", Attribute.IntegerType.getInstance()));
        attributes.add(new Attribute("Test varchar attr", new Attribute.VarcharType(32)));
        Table table = new Table(bufferManager, BufferManager.METAINF_FIRST_PAGE, attributes, "name", new HashMap<Attribute, BxTree>());
        Object[] record = new Object[2];
        record[0] = 123454;
        record[1] = "value";
        for (int i = 0; i < COUNT; i ++) {
            table.insertRecord(record);
        }
        int i = 0;
        for(Record rec: table) {
            Map<SecondLevelId, Object> recAtributes = rec.getAttributes();
            if ((Integer)recAtributes.get(new SecondLevelId("name", "Test int attr")) != 123454) {
				throw new RuntimeException();
			}
			if (!"value".equals(recAtributes.get(new SecondLevelId("name", "Test varchar attr")))) {
				throw new RuntimeException();
			}
            i++;
        }
        if(i != COUNT) {
            throw new RuntimeException();
        }
    }

    private static void testBaseTable() throws FileNotFoundException, ExecutionException {
        new File("db_test_2").delete();
        BufferManager bufferManager = new BufferManager("db_test_2");
        TableBase table = new TableBase(bufferManager, BufferManager.METAINF_FIRST_PAGE, RECORD_SIZE);

        byte[] row = new byte[RECORD_SIZE];
        for (int i = 0; i < COUNT; i ++) {
            row[i % RECORD_SIZE] += 1;
            row[0] = 7;
            table.insert(row);
        }

        int i = 0;
        for(ViewWithId recordView: table) {
            i++;
            if(recordView.getView().getByte(0) != 7) {
                throw new RuntimeException();
            }
            recordView.getView().close();
        }
        if(i != COUNT) {
            throw new RuntimeException();
        }
    }

}
