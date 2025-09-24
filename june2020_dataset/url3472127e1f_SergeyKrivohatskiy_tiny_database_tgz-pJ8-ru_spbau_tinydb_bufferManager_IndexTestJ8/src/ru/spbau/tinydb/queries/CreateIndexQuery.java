package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.engine.IDataBase;

import java.util.Collections;
import java.util.List;

/**
 * @author adkozlov
 */
public class CreateIndexQuery extends TableNameContainer implements IQuery<Boolean> {

    @NotNull
    private final String indexName;
    @NotNull
    private final List<String> attributeNames;
    private final boolean isUnique;
    private final boolean isAscending;
    private final boolean isUsingHash;

    public CreateIndexQuery(@NotNull String tableName, @NotNull String indexName, @NotNull List<String> attributeNames,
                            boolean isUnique, boolean isAscending, boolean isUsingHash) {
        super(tableName);
        this.indexName = indexName;
        this.attributeNames = Collections.unmodifiableList(attributeNames);
        this.isUnique = isUnique;
        this.isAscending = isAscending;
        this.isUsingHash = isUsingHash;
    }

    @NotNull
    public String getIndexName() {
        return indexName;
    }

    @NotNull
    public List<String> getAttributeNames() {
        return attributeNames;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public boolean isAscending() {
        return isAscending;
    }

    public boolean isUsingHash() {
        return isUsingHash;
    }

    @Override
    @NotNull
    public Boolean execute(@NotNull IDataBase instance) throws DBException {
    	return instance.createIndex(getTableName(), getAttributeNames());
    }

    @NotNull
    @Override
    public String toString() {
        return "CreateIndexQuery{" +
                "indexName='" + indexName + '\'' +
                ", attributeNames=" + attributeNames +
                ", isUnique=" + isUnique +
                ", isAscending=" + isAscending +
                ", isUsingHash=" + isUsingHash +
                "} " + super.toString();
    }
}
