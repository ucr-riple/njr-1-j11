package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.expressions.Expression;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.Map;

/**
 * @author adkozlov
 */
public class BooleanFactor extends AbstractBooleanExpression<Expression<Boolean>> {

    public BooleanFactor(@NotNull Expression<Boolean> first) {
        super(first);
    }

    @NotNull
    @Override
    public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
        return getFirst().execute(values);
    }
}
