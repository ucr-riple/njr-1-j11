package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.expressions.PrintableOperation;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.Map;

/**
 * @author adkozlov
 */
public class AndExpression extends AbstractBooleanExpression<BooleanFactor> implements PrintableOperation {

    private final boolean negate;

    public AndExpression(@NotNull BooleanFactor first, boolean negate) {
        super(first);
        this.negate = negate;
    }

    @NotNull
    @Override
    public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
        boolean result = getFirst().execute(values);

        return !negate ? result : !result;
    }

    @NotNull
    @Override
    public String toString() {
        String result = getFirst().toString();

        return !negate ? result : operationToString() + result;
    }

    @NotNull
    @Override
    public String operationToString() {
        return "NOT ";
    }
}
