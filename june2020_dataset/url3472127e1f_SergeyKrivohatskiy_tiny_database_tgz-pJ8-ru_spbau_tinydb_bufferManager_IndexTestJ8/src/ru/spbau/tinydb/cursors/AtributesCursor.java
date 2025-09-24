package ru.spbau.tinydb.cursors;

import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.table.Record;

import java.util.Iterator;
import java.util.Map;

public class AtributesCursor implements
        Iterator<Map<SecondLevelId, Object>> {

    private final Iterator<Record> baseIterator;

    public AtributesCursor(Iterator<Record> recordsIterator) {
        this.baseIterator = recordsIterator;
    }

    @Override
    public boolean hasNext() {
        return baseIterator.hasNext();
    }

    @Override
    public Map<SecondLevelId, Object> next() {
        return baseIterator.next().getAttributes();
    }

	@Override
	public void remove() {
		baseIterator.remove();
	}

}
