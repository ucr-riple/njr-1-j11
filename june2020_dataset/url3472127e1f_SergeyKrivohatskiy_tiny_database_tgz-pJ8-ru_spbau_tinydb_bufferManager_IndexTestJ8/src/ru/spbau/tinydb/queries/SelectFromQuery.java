package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.engine.IDataBase;

import java.util.*;

/**
 * @author adkozlov
 */
public class SelectFromQuery implements IQuery<Iterator<Map<SecondLevelId, Object>>> {

    @NotNull
    private final SelectionTable table;
    @NotNull
    private final List<String> attributes;
    @Nullable
    private final WhereCondition filter;

    public SelectFromQuery(@NotNull SelectionTable table, @Nullable List<String> attributes, @Nullable WhereCondition filter) {
        this.table = table;
        this.attributes = Collections.unmodifiableList(attributes != null ? attributes : new ArrayList<String>());
        this.filter = filter;
    }

    @NotNull
    public SelectionTable getTable() {
        return table;
    }

    @NotNull
    public List<String> getAttributes() {
        return attributes;
    }

    @Nullable
    public WhereCondition getFilter() {
        return filter;
    }

    @Override
    @NotNull
    public Iterator<Map<SecondLevelId, Object>> execute(@NotNull IDataBase instance) throws DBException {
        return instance.select(getTable(), getFilter());
    }

    @NotNull
    @Override
    public String toString() {
        return "SelectFromQuery{" +
                "table=" + table +
                ", attributes=" + attributes +
                ", filter=" + filter +
                "} ";
    }
}
