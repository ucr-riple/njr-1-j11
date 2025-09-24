package ru.spbau.tinydb.expressions;

import org.jetbrains.annotations.NotNull;

/**
 * @author adkozlov
 */
public interface PrintableOperation {

    @NotNull
    String operationToString();
}
