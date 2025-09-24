package ru.spbau.tinydb.engine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.queries.Attribute;
import ru.spbau.tinydb.queries.SecondLevelId;
import ru.spbau.tinydb.queries.SelectionTable;
import ru.spbau.tinydb.queries.WhereCondition;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public interface IDataBase extends AutoCloseable {

    public int insert(@NotNull String tableName, @NotNull List<String> attributes, @NotNull List<Object> record)
            throws DBException;

    public int delete(@NotNull String tableName, @NotNull WhereCondition filter) throws DBException;

    public boolean createTable(@NotNull String tableName,
                               @NotNull Collection<Attribute> schema) throws DBException;

    public void flush();

    @NotNull
    public Iterator<Map<SecondLevelId, Object>> select(@NotNull SelectionTable table, @Nullable WhereCondition filter);

	public boolean createIndex(String tableName, List<String> attributeNames) throws DBException;
}
