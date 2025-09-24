package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;

/**
 * @author adkozlov
 */
public abstract class TableNameContainer {

    @NotNull
    private final String tableName;

    public TableNameContainer(@NotNull String tableName) {
        this.tableName = tableName;
    }

    @NotNull
    public String getTableName() {
        return tableName;
    }

    @NotNull
    @Override
    public String toString() {
        return "TableNameContainer{" +
                "tableName='" + tableName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TableNameContainer)) {
            return false;
        }

        TableNameContainer that = (TableNameContainer) o;

        return tableName.equals(that.tableName);
    }

    @Override
    public int hashCode() {
        return tableName.hashCode();
    }
}
