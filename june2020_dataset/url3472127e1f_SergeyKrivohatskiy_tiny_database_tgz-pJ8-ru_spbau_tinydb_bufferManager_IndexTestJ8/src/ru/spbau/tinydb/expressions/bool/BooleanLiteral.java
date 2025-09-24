package ru.spbau.tinydb.expressions.bool;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.expressions.Expression;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public abstract class BooleanLiteral implements Expression<Boolean> {

    @NotNull
    @Override
    public List<SecondLevelId> getIds() {
        return new LinkedList<>();
    }

    public static class TrueBooleanLiteral extends BooleanLiteral {

        @NotNull
        private static final TrueBooleanLiteral INSTANCE = new TrueBooleanLiteral();

        private TrueBooleanLiteral() {
        }

        @NotNull
        public static TrueBooleanLiteral getInstance() {
            return INSTANCE;
        }

        @NotNull
        @Override
        public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
            return true;
        }

        @NotNull
        @Override
        public String toString() {
            return "TRUE";
        }
    }

    public static class FalseBooleanLiteral extends BooleanLiteral {

        @NotNull
        private static final FalseBooleanLiteral INSTANCE = new FalseBooleanLiteral();

        private FalseBooleanLiteral() {
        }

        @NotNull
        public static FalseBooleanLiteral getInstance() {
            return INSTANCE;
        }

        @NotNull
        @Override
        public Boolean execute(@NotNull Map<SecondLevelId, Object> values) {
            return false;
        }

        @NotNull
        @Override
        public String toString() {
            return "FALSE";
        }
    }
}
