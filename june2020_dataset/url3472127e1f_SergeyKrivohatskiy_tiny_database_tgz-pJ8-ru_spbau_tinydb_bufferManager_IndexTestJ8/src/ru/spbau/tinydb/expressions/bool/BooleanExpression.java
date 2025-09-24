package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author adkozlov
 */
public class BooleanExpression extends BinaryBooleanExpression<OrExpression> {

    public BooleanExpression(@NotNull OrExpression first, @Nullable OrExpression second) {
        super(first, second);
    }

    @NotNull
    @Override
    protected Boolean executeBinaryOperation(@NotNull Boolean first, @NotNull Boolean second) {
        return first || second;
    }

    @NotNull
    @Override
    public String operationToString() {
        return "OR";
    }
}
