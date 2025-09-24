package tp1;

import com.sun.istack.internal.NotNull;
import static tp1.Condition.*;

public class BoolColumn extends Column<Boolean> {

    public BoolColumn(@NotNull String name){
        super(name);
    }

    public Condition and(@NotNull Statement<Boolean> other) {
        return condition(Operator.AND, this, other);
    }
    public Condition or(@NotNull Statement<Boolean> other) {
        return condition(Operator.OR, this, other);
    }

    public Condition not() {
        return condition(Operator.NOT, this);
    }

}
