package ru.spbau.tinydb.utils;

import ru.spbau.tinydb.btree.BxTreeEntry;
import ru.spbau.tinydb.queries.Attribute;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.table.Record;
import ru.spbau.tinydb.table.Table;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * tiny_database
 * Created by Sergey on 08.11.2014.
 */
public class Utils {
    public static byte[] intToBytes(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public static int bytesToInt(byte[] intBytes) {
        return ByteBuffer.wrap(intBytes, 0, 4).getInt();
    }

    public static byte[] stringToBytes(String value, int size) throws UnsupportedEncodingException {
        byte[] res = new byte[size];
        byte[] stringBytes = value.getBytes("US-ASCII");
        if(stringBytes.length > size) {
            throw new IllegalArgumentException("String len is more than size");
        }
        System.arraycopy(stringBytes, 0, res, 0, stringBytes.length);
        Arrays.fill(res, stringBytes.length, res.length, (byte) 0);
        return res;
    }

    public static String bytesToString(byte[] bytes) {
        try {
            int i = 0;
            for(; i < bytes.length; i++) {
                if(bytes[i] == 0) {
                    break;
                }
            }
            return new String(bytes, 0, i, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            // Should not be
            throw new RuntimeException();
        }
    }
    
    public static byte[] doubleToBytes(double d) {
    	byte[] bytes = new byte[8];
    	ByteBuffer.wrap(bytes).putDouble(d);
    	return bytes;
    }
    
    public static Double bytesToDouble(byte[] bytes) {
    	return ByteBuffer.wrap(bytes).getDouble();
    }

    public static void printSchema(Collection<Attribute> schema) {
    	for(Attribute attr: schema) {
    		System.out.print("| " + attr.getAttributeName() + " ");
    	}
		System.out.println("|");
	}

    public static void printAll(Iterator<Object[]> cursor) {
        while (cursor.hasNext()){
            System.out.println(Arrays.toString(cursor.next()));
        }
    }
    public static Iterator<Record> indexIterToRecordIter(final Table table,
			final Iterator<BxTreeEntry> indexIter) {
		return new Iterator<Record>() {
			private Record rec = getNext();
			@Override
			public Record next() {
				if(!hasNext()) {
					throw new IllegalStateException();
				}
				Record oldRec = rec;
				rec = getNext();
				return oldRec;
			}
			
			private Record getNext() {
				try {
					while(indexIter.hasNext()) {
						BxTreeEntry entry = indexIter.next();
						int recordId = entry.value;
						Map<SecondLevelId, Object> atrs = table.getRecord(recordId);
						if(atrs != null) {
							return new Record(atrs, recordId);
						}
					}
					return null;
				} catch (ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public boolean hasNext() {
				return rec != null;
			}

			@Override
			public void remove() {
				throw new IllegalStateException();
			}
		};
	}
    
    public static <T> Iterator<T> emptyIterator() {
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				throw new IllegalStateException();
			}

			@Override
			public void remove() {
				throw new RuntimeException();
			}
		};
    }

	public static Map<SecondLevelId, Object> join(
			Map<SecondLevelId, Object> firstVal,
			Map<SecondLevelId, Object> secondVal) {
		Map<SecondLevelId, Object> result = new HashMap<>();
		result.putAll(firstVal);
		result.putAll(secondVal);
		return result;
	}
}
