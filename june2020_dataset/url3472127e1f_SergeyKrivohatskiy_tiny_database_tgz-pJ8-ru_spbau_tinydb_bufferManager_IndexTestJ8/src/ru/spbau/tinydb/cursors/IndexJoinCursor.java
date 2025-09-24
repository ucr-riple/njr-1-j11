package ru.spbau.tinydb.cursors;

import java.util.Iterator;
import java.util.Map;

import ru.spbau.tinydb.btree.BxTree;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.table.Record;
import ru.spbau.tinydb.table.Table;
import ru.spbau.tinydb.utils.Utils;

public class IndexJoinCursor implements Iterator<Map<SecondLevelId, Object>> {

	private final Table joinTable;
	private final BxTree index;
	private Map<SecondLevelId, Object> row;
	private final Iterator<Map<SecondLevelId, Object>> baseCursor;
    private Iterator<Map<SecondLevelId, Object>> secondCursor;
	private final SecondLevelId id;

	public IndexJoinCursor(Iterator<Map<SecondLevelId, Object>> cursor,
			Table joinTable, BxTree index, SecondLevelId id) {
			this.baseCursor = cursor;
			this.joinTable = joinTable;
			this.index = index;
			this.id = id;
			row = getRow();
	}

	private Map<SecondLevelId, Object> getRow() {
		while(true) {
			if(secondCursor != null && secondCursor.hasNext()) {
				return secondCursor.next();
			}
			if(!baseCursor.hasNext()) {
				return null;
			}
			final Map<SecondLevelId, Object> tmp = baseCursor.next();
			Integer key = (Integer) tmp.get(id);
			final Iterator<Record> iter = Utils.indexIterToRecordIter(joinTable, index.find(key, key, true, true));
			secondCursor = new Iterator<Map<SecondLevelId,Object>>() {
				private final Iterator<Map<SecondLevelId,Object>> base = new AtributesCursor(iter);
				@Override
				public boolean hasNext() {
					return base.hasNext();
				}

				@Override
				public Map<SecondLevelId, Object> next() {
					return Utils.join(tmp, base.next());
				}

				@Override
				public void remove() {
					throw new RuntimeException();
				}
			};
		}
	}

	@Override
	public boolean hasNext() {
		return row != null;
	}

	@Override
	public Map<SecondLevelId, Object> next() {
		if(!hasNext()) {
			throw new IllegalStateException();
		}
		Map<SecondLevelId, Object> oldRow = row;
		row = getRow();
		return oldRow;
	}

	@Override
	public void remove() {
        throw new DBException("Remove is not implemented for IndexJoinCursor cursor");
	}

}
