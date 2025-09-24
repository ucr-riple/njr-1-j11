package ru.spbau.tinydb.expressions;

import org.jetbrains.annotations.NotNull;

/**
 * @author adkozlov
 */
public class AssignmentExpression<V> implements PrintableOperation {

    @NotNull
    private final String attributeName;
    @NotNull
    private final V value;

    public AssignmentExpression(@NotNull String attributeName, @NotNull V value) {
        this.attributeName = attributeName;
        this.value = value;
    }

    @NotNull
    @Override
    public String toString() {
        return attributeName + " " + operationToString() + " " + value;
    }

    @NotNull
    @Override
    public String operationToString() {
        return "=";
    }
}
