package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;

/**
 * @author adkozlov
 */
public class SecondLevelId extends TableNameContainer {

    @NotNull
    private final String attributeName;

    public SecondLevelId(@NotNull String tableName, @NotNull String attributeName) {
        super(tableName);
        this.attributeName = attributeName;
    }

    @NotNull
    public String getAttributeName() {
        return attributeName;
    }

    @NotNull
    @Override
    public String toString() {
        return getTableName() + "." + attributeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecondLevelId)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SecondLevelId that = (SecondLevelId) o;

        return attributeName.equals(that.attributeName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + attributeName.hashCode();
        return result;
    }
}
