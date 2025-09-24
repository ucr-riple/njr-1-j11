package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author adkozlov
 */
public class OrExpression extends BinaryBooleanExpression<AndExpression> {

    public OrExpression(@NotNull AndExpression first, @Nullable AndExpression second) {
        super(first, second);
    }

    @NotNull
    @Override
    protected Boolean executeBinaryOperation(@NotNull Boolean first, @NotNull Boolean second) {
        return first && second;
    }

    @NotNull
    @Override
    public String operationToString() {
        return "AND";
    }
}
