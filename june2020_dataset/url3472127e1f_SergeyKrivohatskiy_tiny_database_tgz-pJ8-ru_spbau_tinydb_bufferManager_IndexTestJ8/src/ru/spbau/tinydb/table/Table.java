package ru.spbau.tinydb.table;

import org.jetbrains.annotations.NotNull;

import ru.spbau.tinydb.btree.BxTree;
import ru.spbau.tinydb.bufferManager.BufferManager;
import ru.spbau.tinydb.bufferManager.BufferView;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.queries.Attribute;
import ru.spbau.tinydb.queries.Attribute.DataType;
import ru.spbau.tinydb.queries.Attribute.DoubleType;
import ru.spbau.tinydb.queries.Attribute.IntegerType;
import ru.spbau.tinydb.queries.Attribute.VarcharType;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

/**
 * tiny_database
 * Created by Sergey on 08.11.2014.
 */
public class Table implements Iterable<Record> {

    @NotNull
    private final TableBase baseTable;
    private final int recordSize;

    @NotNull
    private final Collection<Attribute> attributes;
	private final Map<String, Attribute> atributeNameToAtribute;
	private final String tableName;
	private final Map<Attribute, BxTree> indexes;
	private HashMap<Attribute, Integer> atrToIdx;

	public Table(@NotNull BufferManager bm, int firstPage, @NotNull Collection<Attribute> attributes,
    		String name, @NotNull Map<Attribute, BxTree> indexes) {
        int recordSize = 0;
        for (Attribute attribute : attributes) {
            recordSize += getAttrSize(attribute);
        }

        this.indexes = indexes;
        this.recordSize = recordSize;
        this.attributes = attributes;
        this.atrToIdx = new HashMap<>();
        atributeNameToAtribute = new HashMap<>();
        int i = 0;
        for(Attribute atr: attributes) {
        	atrToIdx.put(atr, i);
        	atributeNameToAtribute.put(atr.getAttributeName(), atr);
        	i += 1;
        }
        tableName = name;
        baseTable = new TableBase(bm, firstPage, recordSize);
    }
	
	public Attribute getAtrByName(String atrName) {
		return atributeNameToAtribute.get(atrName);
	}

    private int getAttrSize(@NotNull Attribute attribute) {
        if (attribute.getDataType() instanceof IntegerType) {
            return 4;
        }
        if (attribute.getDataType() instanceof DoubleType) {
            return 8;
        }
        if (attribute.getDataType() instanceof VarcharType) {
            VarcharType vcType = (VarcharType) attribute.getDataType();
            return vcType.getLength();
        }

        throw new DBException("Unsupported attribute type");
    }
    
	public BxTree getIndex(Attribute atr) {
    	return indexes.get(atr);
    }

    @NotNull
    public Collection<Attribute> getSchema() {
        return attributes;
    }

	public int insertRecord(@NotNull Object[] record) throws ExecutionException, UnsupportedEncodingException {
    	byte[] row = new byte[recordSize];

        int pos = 0;
        int i = 0;
        for (Attribute attr : attributes) {
            byte[] attrValue = toByteArray(record[i], attr.getDataType());
            i += 1;
            System.arraycopy(attrValue, 0, row, pos, attrValue.length);
            pos += attrValue.length;
        }

        int id = baseTable.insert(row);
        
    	for(Entry<Attribute, BxTree> entry: indexes.entrySet()) {
    		Object key = record[indexOf(entry.getKey())];
    		entry.getValue().insert((Integer) key, id);
    	}
        return id;
    }
    
    private int indexOf(Attribute key) {
		return atrToIdx.get(key);
	}

	public boolean remove(int recordId) {
        return baseTable.remove(recordId);
    }

    @NotNull
    private byte[] toByteArray(@NotNull Object object, @NotNull DataType dataType) throws UnsupportedEncodingException {
        if (dataType instanceof IntegerType) {
            return Utils.intToBytes((Integer) object);
        }
        if (dataType instanceof DoubleType) {
            return Utils.doubleToBytes((Double) object);
        }
        if (dataType instanceof VarcharType) {
            VarcharType vcType = (VarcharType) dataType;
        	return Utils.stringToBytes((String)object, vcType.getLength());
        }

        throw new DBException("Unsupported attribute type");
    }

	public Map<SecondLevelId, Object> getRecord(int recordId) throws ExecutionException {
        try (BufferView recordView = baseTable.get(recordId)) {
        	if(recordView == null) {
        		return null;
        	}
            return getRecordFromView(recordView);
        }
    }

    private Map<SecondLevelId, Object> getRecordFromView(BufferView recordView) {
        int pos = 0;
        Map<SecondLevelId, Object> record = new HashMap<>();
        for(Attribute attr: attributes) {
            int len = getAttrSize(attr);
            byte[] attrValue = recordView.getBytes(pos, len);
            Object value = byteArrayToObject(attrValue, attr.getDataType());
            record.put(new SecondLevelId(tableName, attr.getAttributeName()), value);
            pos += len;
        }
        return record;
    }

    private Object byteArrayToObject(byte[] attrValue, DataType dataType) {
        if (dataType instanceof IntegerType) {
            return Utils.bytesToInt(attrValue);
        }
        if (dataType instanceof DoubleType) {
            return Utils.bytesToDouble(attrValue);
        }
        if (dataType instanceof VarcharType) {
            return Utils.bytesToString(attrValue);
        }

        throw new DBException("Unsupported attribute type");
    }

	@Override
    public Iterator<Record> iterator() {
        return new Iterator<Record>() {
            private Iterator<ViewWithId> baseIterator = baseTable.iterator();

            @Override
            public boolean hasNext() {
                return baseIterator.hasNext();
            }

            @Override
            public Record next() {
                ViewWithId viewWithId = baseIterator.next();
                try(BufferView recordView = viewWithId.getView()) {
                    Map<SecondLevelId, Object> value = getRecordFromView(recordView);
                    return new Record(value, viewWithId.getId());
                }
            }

            @Override
            public void remove() {
                baseIterator.remove();
            }
        };
    }
}
