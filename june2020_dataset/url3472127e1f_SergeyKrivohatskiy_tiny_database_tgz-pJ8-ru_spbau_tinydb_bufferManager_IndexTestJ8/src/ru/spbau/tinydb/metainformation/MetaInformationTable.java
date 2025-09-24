package ru.spbau.tinydb.metainformation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.tinydb.btree.BxTree;
import ru.spbau.tinydb.bufferManager.BufferManager;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.queries.Attribute;
import ru.spbau.tinydb.queries.Attribute.DoubleType;
import ru.spbau.tinydb.queries.Attribute.IntegerType;
import ru.spbau.tinydb.queries.Attribute.VarcharType;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.table.Record;
import ru.spbau.tinydb.table.Table;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * tiny_database
 * Created by Sergey on 08.11.2014.
 */
public class MetaInformationTable {
	// Tables stored in the next format:
	// Table1_name | begin_page | atr_count
	// Atr1_name   | atr_type   | atr_param
	// Atr2_name   | atr_type   | atr_param
	// ...
	// AtrLast_name| atr_type   | atr_param
	// Table2_name | begin_page | atr_count 
	// ...

    private final static Collection<Attribute> META_TABLE_SCHEME =
            Arrays.asList(new Attribute("name", new Attribute.VarcharType(255)),
                    new Attribute("val1", Attribute.IntegerType.getInstance()),
                    new Attribute("val2", Attribute.IntegerType.getInstance()));
    private final static SecondLevelId NAME_ID = new SecondLevelId("metainf", "name");
    private final static SecondLevelId VAL1_ID = new SecondLevelId("metainf", "val1");
    private final static SecondLevelId VAL2_ID = new SecondLevelId("metainf", "val2");
	private static final Collection<Attribute> INDEXES_TABLE_SCHEME = 
            Arrays.asList(new Attribute("tableName", new Attribute.VarcharType(255)),
                    new Attribute("atrIdx", Attribute.IntegerType.getInstance()),
                    new Attribute("indexFirstPage", Attribute.IntegerType.getInstance()));
    private final static SecondLevelId TABLE_NAME = new SecondLevelId("indexes", "tableName");
    private final static SecondLevelId ATR_IDX = new SecondLevelId("indexes", "atrIdx");
    private final static SecondLevelId INDEX_FIRST_PAGE = new SecondLevelId("indexes", "indexFirstPage");

    @NotNull
    private final BufferManager bufferManager;
    @NotNull
    private final Table table;
    @NotNull
    private final Table indexesTable;
    private static final int MAX_TABLE_CACHE_ITEMS = 10;
    private final Map<String, Table> tableCache = new HashMap<>();

    public MetaInformationTable(@NotNull BufferManager bufferManager) {
        this.bufferManager = bufferManager;
        table = new Table(bufferManager, BufferManager.METAINF_FIRST_PAGE, 
        		META_TABLE_SCHEME, "metainf", new HashMap<Attribute, BxTree>());
        indexesTable = new Table(bufferManager, BufferManager.INDEXES_FIRST_PAGE, 
        		INDEXES_TABLE_SCHEME, "indexes", new HashMap<Attribute, BxTree>());
    }
    
	@Nullable
    public Table loadTable(@NotNull String name) {

		if(tableCache.containsKey(name)) {
			return tableCache.get(name);
		}
		
        int ignore = 0;
        int attributesCount = 0;
        int firstPage = 0;
        List<Attribute> attributes = null;
        for(Record rec: table) {
            Map<SecondLevelId, Object> recAtributes = rec.getAttributes();
            if(ignore != 0) {
                ignore -= 1;
                continue;
            }
            String currName = (String) recAtributes.get(NAME_ID);
            if(attributes != null) {
                attributesCount -= 1;
                Attribute.DataType type = integersToType(recAtributes);
                Attribute atr = new Attribute(currName, type);
                attributes.add(atr);
                if(attributesCount == 0) {
            		Map<Attribute, BxTree> indexes = loadIndexes(name, attributes);
                    Table result = new Table(bufferManager, firstPage, 
                    		attributes, name, indexes);
                    if(tableCache.size() == MAX_TABLE_CACHE_ITEMS) {
                    	tableCache.remove(tableCache.keySet().iterator().next());
                    }
                    tableCache.put(name, result);
                    return result;
                }
                continue;
            }
            if(!name .equals(currName)) {
                ignore = (Integer)recAtributes.get(VAL2_ID);
                continue;
            }
            attributes = new ArrayList<>();
            attributesCount = (Integer)recAtributes.get(VAL2_ID);
            firstPage = (Integer)recAtributes.get(VAL1_ID);
        }

        return null;
    }

    private Map<Attribute, BxTree> loadIndexes(String tableName, List<Attribute> attributes) {
    	Map<Attribute, BxTree> indexes = new HashMap<>();
    	for(Record rec: indexesTable) {
    		if(!tableName.equals((String) rec.getAttributes().get(TABLE_NAME))) {
    			continue;
    		}
    		Integer firstPage = (Integer) rec.getAttributes().get(INDEX_FIRST_PAGE);
    		Integer atrIdx = (Integer) rec.getAttributes().get(ATR_IDX);
    		indexes.put(attributes.get(atrIdx), new BxTree(bufferManager, firstPage));
    	}
		return indexes;
	}
    
    public boolean createIndex(String tableName, int atributeIdx, String attributeName) {
    	for(Record rec: indexesTable) {
    		if(!tableName.equals((String) rec.getAttributes().get(TABLE_NAME))) {
    			continue;
    		}
    		Integer atrIdx = (Integer) rec.getAttributes().get(ATR_IDX);
    		if(atrIdx == atributeIdx) {
    			// Index already exists
    			return false;
    		}
    	}
    	try {
        	Table table = loadTable(tableName);
        	int indexFirstPage = bufferManager.getFreePage();
        	Object[] indexRecord = {tableName, atributeIdx, indexFirstPage};
			indexesTable.insertRecord(indexRecord);
			
			BxTree index = new BxTree(bufferManager, indexFirstPage);
			SecondLevelId atrId = new SecondLevelId(tableName, attributeName);
			for(Record rec: table) {
				Integer val = (Integer)rec.getAttributes().get(atrId);
				index.insert(val, rec.getRecordId());
			}
			tableCache.remove(tableName);
			return true;
		} catch (UnsupportedEncodingException e) {
			// Cannot be
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
    }

	@NotNull
    private Attribute.DataType integersToType(Map<SecondLevelId, Object> row) {
        switch ((Integer)row.get(VAL1_ID)){
            case 1:
                return Attribute.IntegerType.getInstance();
            case 2:
                return Attribute.DoubleType.getInstance();
            case 3:
                return new Attribute.VarcharType((Integer) row.get(VAL2_ID));
        }

        throw new DBException("Unknown type");
    }

    @NotNull
    public Table createTable(@NotNull String name, @NotNull Collection<Attribute> schema) throws UnsupportedEncodingException, ExecutionException {
    	int firstPage = bufferManager.getFreePage();
        table.insertRecord(createRecord(name, firstPage, schema.size()));

        for (Attribute attribute : schema) {
            table.insertRecord(createRecord(attribute));
        }
        return new Table(bufferManager, firstPage, schema, name, new HashMap<Attribute, BxTree>());
    }

    @NotNull
    private static Object[] createRecord(@NotNull String name) {
        Object[] result = new Object[3];
        result[0] = name;

        return result;
    }

    @NotNull
    private static Object[] createRecord(@NotNull String name, int firstPage, int size) {
        Object[] result = createRecord(name);

        result[1] = firstPage;
        result[2] = size;

        return result;
    }

    @NotNull
    private static Object[] createRecord(@NotNull Attribute attribute) {
        Object[] result = createRecord(attribute.getAttributeName());

        Attribute.DataType dataType = attribute.getDataType();
        if (dataType instanceof IntegerType) {
            result[1] = 1;
            result[2] = 0;
        } else if (dataType instanceof DoubleType) {
            result[1] = 2;
            result[2] = 0;
        } else if (dataType instanceof VarcharType) {
            result[1] = 3;
            result[2] = ((VarcharType) dataType).getLength();
        } else {
            throw new DBException("Unknown type");
        }

        return result;
    }
}
