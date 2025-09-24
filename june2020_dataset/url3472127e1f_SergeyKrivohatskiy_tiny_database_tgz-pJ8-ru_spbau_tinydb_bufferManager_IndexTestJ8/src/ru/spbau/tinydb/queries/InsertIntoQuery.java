package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.engine.IDataBase;

import java.util.Collections;
import java.util.List;

/**
 * @author adkozlov
 */
public class InsertIntoQuery extends TableNameContainer implements IQuery<Integer> {

    @NotNull
    private final List<String> attributes;
    @NotNull
    private final List<Object> values;

    public InsertIntoQuery(@NotNull String tableName, @NotNull List<String> attributes, @NotNull List<Object> values) {
        super(tableName);
        this.attributes = Collections.unmodifiableList(attributes);
        this.values = Collections.unmodifiableList(values);
    }

    @NotNull
    public List<String> getAttributes() {
        return attributes;
    }

    @NotNull
    public List<Object> getValues() {
        return values;
    }

    @NotNull
    @Override
    public Integer execute(@NotNull IDataBase instance) throws DBException {
        return instance.insert(getTableName(), getAttributes(), getValues());
    }

    @NotNull
    @Override
    public String toString() {
        return "InsertIntoQuery{" +
                "attributes=" + attributes +
                ", values=" + values +
                "} " + super.toString();
    }
}
