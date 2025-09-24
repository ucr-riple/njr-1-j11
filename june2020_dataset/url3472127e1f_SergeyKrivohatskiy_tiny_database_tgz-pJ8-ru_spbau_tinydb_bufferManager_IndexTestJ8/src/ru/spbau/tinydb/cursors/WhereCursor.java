package ru.spbau.tinydb.cursors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.queries.WhereCondition;
import ru.spbau.tinydb.table.Record;

import java.util.Iterator;

/**
 * tiny_database
 * Created by Sergey on 09.11.2014.
 */
public class WhereCursor implements Iterator<Record> {

    @NotNull
    private final Iterator<Record> baseCursor;
    @Nullable
    private final WhereCondition condition;
    @Nullable
    private Record record;

    public WhereCursor(@NotNull Iterator<Record> baseCursor, @Nullable WhereCondition condition) {
        this.baseCursor = baseCursor;
        this.condition = condition;

        record = getValues();
    }

    @Nullable
    private Record getValues() {
        while (baseCursor.hasNext()) {
            Record values = baseCursor.next();

            if (condition == null || condition.check(values.getAttributes())) {
                return values;
            }
        }

        return null;
    }

    @Override
    public boolean hasNext() {
        return record != null;
    }

    @Nullable
    @Override
    public Record next() {
        Record oldRecord = record;
        record = getValues();

        return oldRecord;
    }

    @Override
    public void remove() {
        throw new DBException("Remove is not implemented for where cursor");
    }
}
