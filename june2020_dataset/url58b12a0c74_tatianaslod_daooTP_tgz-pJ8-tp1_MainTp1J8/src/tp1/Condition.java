package tp1;

import com.sun.istack.internal.NotNull;

public abstract class Condition extends Statement<Boolean>{

    public static Condition condition(@NotNull Operator operator,@NotNull Statement<?> left,@NotNull Statement<?> right){
        return new BinaryCondition(operator,left,right);
    }
    public static Condition condition(Operator operator, Statement<?> left){
        return new UnitaryCondition(operator,left);
    }

    public Condition and(@NotNull Statement<Boolean> other) {
        return new BinaryCondition(Operator.AND, this, other);
    }
    public Condition or(@NotNull Statement<Boolean> other) {
        return new BinaryCondition(Operator.OR, this, other);
    }

    public Condition not() {
        return new UnitaryCondition(Operator.NOT, this);
    }


    public abstract Operator getOperator();

    public abstract boolean isEmpty();
}
