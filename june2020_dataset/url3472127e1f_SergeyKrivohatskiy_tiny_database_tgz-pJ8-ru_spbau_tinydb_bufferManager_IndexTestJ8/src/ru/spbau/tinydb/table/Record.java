package ru.spbau.tinydb.table;

import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.Map;

public class Record {
    private final Map<SecondLevelId, Object> attributes;
    private final int recordId;
    
    public Record(Map<SecondLevelId, Object> value, int recordId) {
        this.attributes = value;
        this.recordId = recordId;
    }

    public int getRecordId() {
        return recordId;
    }

    public Map<SecondLevelId, Object> getAttributes() {
        return attributes;
    }
}
