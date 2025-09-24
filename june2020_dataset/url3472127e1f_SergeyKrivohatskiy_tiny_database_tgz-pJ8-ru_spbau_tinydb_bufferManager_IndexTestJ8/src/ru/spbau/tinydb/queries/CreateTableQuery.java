package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.engine.IDataBase;

import java.util.Collections;
import java.util.List;

/**
 * @author adkozlov
 */
public class CreateTableQuery extends TableNameContainer implements IQuery<Boolean> {

    @NotNull
    private final List<Attribute> attributes;

    public CreateTableQuery(@NotNull String tableName, @NotNull List<Attribute> attributes) {
        super(tableName);
        this.attributes = Collections.unmodifiableList(attributes);
    }

    public @NotNull List<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    @NotNull
    public Boolean execute(@NotNull IDataBase instance) throws DBException {
        return instance.createTable(getTableName(), getAttributes());
    }

    @NotNull
    @Override
    public String toString() {
        return "CreateTableQuery{" +
                "attributes=" + attributes +
                "} " + super.toString();
    }
}
