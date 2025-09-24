package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import static tp1.Condition.*;


public class Column<T extends Comparable<T>> extends Value<T>{
    @NotNull private final String columnName;

    public Column(@NotNull String columnName){
        this.columnName = columnName;
    }

    public Condition isNull() {
        return condition(Operator.IS_NULL, this);
    }

    public Condition isNotNull() {
        return condition(Operator.IS_NOT_NULL, this);
    }

    public Condition greaterThan(@NotNull Value<Integer> value) {
        return condition(Operator.GREAT, this, value);
    }

    public Condition greaterEqualThan(@NotNull Value<Integer> value) {
        return condition(Operator.GREAT, this, value);
    }

    public Condition lessThan(@NotNull Value<Integer> value) {
        return condition(Operator.LESS, this, value);
    }

    public Condition equalTo(@NotNull Value<Integer> value) {
        return condition(Operator.EQ, this, value);
    }

    public Condition ne(@NotNull Value<Integer> value) {
        return condition(Operator.NE, this, value);
    }

    public String getName(){
        return columnName;
    }

    public void accept(@NotNull QueryVisitor visitor){
        visitor.visit(this);
    }
}
