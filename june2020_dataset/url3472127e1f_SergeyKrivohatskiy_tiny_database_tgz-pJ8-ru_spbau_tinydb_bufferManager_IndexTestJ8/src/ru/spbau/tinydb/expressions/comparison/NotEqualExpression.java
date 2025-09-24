package ru.spbau.tinydb.expressions.comparison;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.Map;

/**
 * @author adkozlov
 */
public class NotEqualExpression<V extends Comparable<V>> extends ComparisonExpression<V> {

    public NotEqualExpression(@NotNull SecondLevelId id, @NotNull V value) {
        super(id, value);
    }

    @NotNull
    @Override
    public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
        return compareWithValueFromMap(values) != 0;
    }

    @NotNull
    @Override
    public String operationToString() {
        return "!=";
    }
}
