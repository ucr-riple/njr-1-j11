package org.dclayer.net.lla.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.lla.LLA;

/**
 * Database for permanently storing lower-level addresses
 * @author Martin Exner
 */
public class LLADatabase implements HierarchicalLevel {
	
	public static class LLADatabaseCursor {
		private int index = 0;
	}
	
	// TODO implement this properly
	
	private ArrayList<Data> llaDatas = new ArrayList<>();
	
	/**
	 * permanently stores the given LLA (having it serialized first, thus not storing the LLA object itself)
	 * @param lla the LLA to store
	 */
	public synchronized void store(LLA lla) {
		Data data = lla.getData();
		llaDatas.add(data);
	}
	
	/**
	 * permanently stores the LLAs in the given list (having them serialized first, thus not storing the LLA objects themselves)
	 * @param llas the list of LLAs to store
	 */
	public synchronized void store(List<LLA> llas) {
		for(LLA lla : llas) {
			this.store(lla);
		}
	}
	
	public synchronized List<LLA> getLLAs(LLADatabaseCursor cursor, int limit) {
		
		LinkedList<LLA> llas = new LinkedList<>();
		
		DataByteBuf dataByteBuf = new DataByteBuf();
		for(int i = 0; i < limit && cursor.index < llaDatas.size();) {
			
			Data data = llaDatas.get(cursor.index++);
			dataByteBuf.setData(data);
			
			try {
				llas.add(LLA.fromByteBuf(dataByteBuf));
			} catch (BufException e) {
				Log.exception(this, e);
				continue;
			} catch (ParseException e) {
				Log.exception(this, e);
				continue;
			}
			
			i++;
			
		}
		
		return llas;
		
	}
	
	public LLADatabaseCursor makeCursor() {
		return new LLADatabaseCursor();
	}
	
	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return null;
	}
	
	@Override
	public String toString() {
		return "LLADatabase";
	}
	
}
