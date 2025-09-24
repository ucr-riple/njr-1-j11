package tp1;

import com.sun.istack.internal.NotNull;

public enum Operator {
    AND("and"),
    OR("or"),
    LESS("<"),
    GREAT(">"),
    EQ("="),
    NE("!="), STARTS("like"), CONTAINS("contains"),  IS_NULL("is null"), IS_NOT_NULL("is not null"), NOT("not"), EMPTY("");
    private final String name;

    private Operator(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
