package ru.spbau.tinydb.bufferManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ru.spbau.tinydb.engine.DataBaseEngine;
import ru.spbau.tinydb.engine.IDataBase;
import ru.spbau.tinydb.expressions.bool.AndExpression;
import ru.spbau.tinydb.expressions.bool.BooleanExpression;
import ru.spbau.tinydb.expressions.bool.BooleanFactor;
import ru.spbau.tinydb.expressions.bool.OrExpression;
import ru.spbau.tinydb.expressions.comparison.EqualExpression;
import ru.spbau.tinydb.expressions.comparison.JoinOnExpression;
import ru.spbau.tinydb.queries.Attribute;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.queries.SelectionTable;
import ru.spbau.tinydb.queries.WhereCondition;

/**
 * Created by Sergey on 09.10.2014.
 */
public class IndexTest {

    private static final int COUNT = 2000;

    public static void main(String[] args) throws IOException, ExecutionException {
        testTable();
    }

    private static void testTable() throws FileNotFoundException, ExecutionException, UnsupportedEncodingException {
        new File("db_test").delete();
        IDataBase db = DataBaseEngine.getDBInstance("db_test");
        List<Attribute> table1Schema = Arrays.asList(new Attribute("id", Attribute.IntegerType.getInstance()),
        		new Attribute("string", new Attribute.VarcharType(245)));
        List<Attribute> table2Schema = Arrays.asList(new Attribute("id", Attribute.IntegerType.getInstance()),
        		new Attribute("id_div10", Attribute.IntegerType.getInstance()));
        db.createTable("test_table1", table1Schema);
        db.createTable("test_table2", table2Schema);
        List<String> table1Atrs = Arrays.asList("id", "string");
        List<String> table2Atrs = Arrays.asList("id", "id_div10");

		long time;
        time = System.nanoTime();
        for(int i = 0; i < COUNT; i ++) {
        	db.insert("test_table1", table1Atrs, Arrays.asList((Object)i, (Object)("test value table 1 id " + i)));
        	db.insert("test_table2", table2Atrs, Arrays.asList((Object)i, (Object)(i / 10)));
        }
        time = (System.nanoTime() - time) / 1000000;
        System.out.println("Load time is " + time + "ms");
        
        SecondLevelId id1 = new SecondLevelId("test_table1", "id");
        SecondLevelId id2 = new SecondLevelId("test_table2", "id");
        WhereCondition where = new WhereCondition(new BooleanExpression(new OrExpression(new AndExpression(new BooleanFactor(
        		new EqualExpression<Integer>(id1, COUNT / 3)), false), null), null));

        System.out.println("warm up");
        checkSelect(db, where);
        checkJoin(db, id1, id2);

        System.out.println("without index");
        checkSelect(db, where);
        checkJoin(db, id1, id2);

        //db.createIndex("test_table1", Arrays.asList("id"));
        db.createIndex("test_table2", Arrays.asList("id"));
        
        System.out.println("index created");
        checkSelect(db, where);
        checkJoin(db, id1, id2);
    }

	private static void checkSelect(IDataBase db, WhereCondition where) {
		long time;
        time = System.nanoTime();
        Iterator<Map<SecondLevelId, Object>> result = db.select(new SelectionTable("test_table1", new ArrayList<JoinOnExpression>()), where);
        
        int count = 0;
        while(result.hasNext()) {
        	count ++;
        	result.next();
        }
        time = (System.nanoTime() - time) / 1000000;
        System.out.println("Select where id == CONST | " + count + " results | Time is " + time + "ms");
	}

	private static void checkJoin(IDataBase db, SecondLevelId id1, SecondLevelId id2) {
		long time;
        time = System.nanoTime();
        Iterator<Map<SecondLevelId, Object>> result = db.select(new SelectionTable("test_table1", Arrays.asList(
        		new JoinOnExpression("test_table2", id1, id2))), null);
        
        int count = 0;
        while(result.hasNext()) {
        	count ++;
        	result.next();
        }
        time = (System.nanoTime() - time) / 1000000;
        System.out.println("Join | " + count + " results | Time is " + time + "ms");
	}

}
