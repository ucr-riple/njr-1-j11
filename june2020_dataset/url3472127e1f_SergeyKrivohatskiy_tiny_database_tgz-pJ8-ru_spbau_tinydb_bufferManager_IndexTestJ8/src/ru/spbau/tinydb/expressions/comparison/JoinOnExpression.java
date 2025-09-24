package ru.spbau.tinydb.expressions.comparison;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.expressions.Expression;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public class JoinOnExpression implements Expression<Boolean> {

    @NotNull
    private final String tableName;
    @NotNull
    private final SecondLevelId firstId;
    @NotNull
    private final SecondLevelId secondId;

    public JoinOnExpression(@NotNull String tableName, @NotNull SecondLevelId firstId, @NotNull SecondLevelId secondId) {
        this.tableName = tableName;
        this.firstId = firstId;
        this.secondId = secondId;
    }

    @NotNull
    public String getTableName() {
        return tableName;
    }

    @NotNull
    public SecondLevelId getFirstId() {
        return firstId;
    }

    @NotNull
    public SecondLevelId getSecondId() {
        return secondId;
    }

    @NotNull
    @Override
    public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
        assertContainsId(values, firstId);
        assertContainsId(values, secondId);

        Object first = values.get(firstId);
        Object second = values.get(secondId);

        assertClassesAreEqual(first, second);

        return first.equals(second);
    }

    @NotNull
    @Override
    public List<SecondLevelId> getIds() {
        List<SecondLevelId> result = new LinkedList<>();
        result.add(firstId);
        result.add(secondId);

        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return "INNER JOIN " + tableName + " ON " + firstId + " = " + secondId;
    }

    private static void assertContainsId(@NotNull Map<SecondLevelId, Object> values, @NotNull SecondLevelId id) {
        if (!values.containsKey(id)) {
            throw new DBException("no such attribute: " + id);
        }
    }

    private static void assertClassesAreEqual(@NotNull Object first, @NotNull Object second) {
        Class<?> firstClass = first.getClass();
        Class<?> secondClass = second.getClass();

        if (!first.getClass().equals(second.getClass())) {
            throw new DBException("incomparable types: " + firstClass + ", " + secondClass);
        }
    }
}
