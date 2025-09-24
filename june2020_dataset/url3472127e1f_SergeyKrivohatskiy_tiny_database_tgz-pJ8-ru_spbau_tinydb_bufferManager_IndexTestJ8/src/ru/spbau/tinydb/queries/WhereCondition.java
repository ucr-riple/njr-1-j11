package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.expressions.bool.BooleanExpression;

import java.util.Map;

/**
 * @author adkozlov
 */
public class WhereCondition {

    @NotNull
    private final BooleanExpression condition;

    public WhereCondition(@NotNull BooleanExpression condition) {
        this.condition = condition;
    }

    public boolean check(Map<SecondLevelId, Object> values) {
        return condition.execute(values);
    }

    @NotNull
    @Override
    public String toString() {
        return "WhereCondition{" +
                "condition=" + condition +
                '}';
    }
}
