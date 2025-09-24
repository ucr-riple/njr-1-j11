package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.expressions.Expression;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.List;

/**
 * @author adkozlov
 */
public abstract class AbstractBooleanExpression<V extends Expression<Boolean>> implements Expression<Boolean> {

    @NotNull
    private final V first;

    public AbstractBooleanExpression(@NotNull V first) {
        this.first = first;
    }

    @NotNull
    public V getFirst() {
        return first;
    }

    @NotNull
    @Override
    public List<SecondLevelId> getIds() {
        return first.getIds();
    }

    @NotNull
    @Override
    public String toString() {
        return getFirst().toString();
    }
}
