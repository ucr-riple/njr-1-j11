package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.tinydb.expressions.Expression;
import ru.spbau.tinydb.expressions.PrintableOperation;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public abstract class BinaryBooleanExpression<V extends Expression<Boolean>> extends AbstractBooleanExpression<V> implements PrintableOperation {

    @Nullable
    private final V second;

    public BinaryBooleanExpression(@NotNull V first, @Nullable V second) {
        super(first);
        this.second = second;
    }

    @Nullable
    public V getSecond() {
        return second;
    }

    @NotNull
    @Override
    public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
        Boolean firstValue = getFirst().execute(values);

        return getSecond() != null ? executeBinaryOperation(firstValue, getSecond().execute(values)) : firstValue;
    }

    @NotNull
    protected abstract Boolean executeBinaryOperation(@NotNull Boolean first, @NotNull Boolean second);

    @NotNull
    @Override
    public List<SecondLevelId> getIds() {
        List<SecondLevelId> result = super.getIds();

        if (second != null) {
            result.addAll(second.getIds());
        }

        return result;
    }

    @NotNull
    @Override
    public String toString() {
        String result = getFirst().toString();

        return getSecond() != null ? "(" + result + " " + operationToString() + " " + getSecond().toString() + ")" : result;
    }
}
