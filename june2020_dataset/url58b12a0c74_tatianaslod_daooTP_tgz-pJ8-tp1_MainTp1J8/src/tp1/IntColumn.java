package tp1;
import com.sun.istack.internal.NotNull;

import static tp1.Condition.condition;
public class IntColumn extends Column<Integer> {

    public IntColumn(@NotNull String name){
        super(name);
    }

    public Condition greaterThan(@NotNull Value<Integer> value) {
        return condition(Operator.GREAT, this, value);
    }
    public Condition lessThan(@NotNull Value<Integer> value) {
        return condition(Operator.LESS, this, value);
    }
    public Condition between(@NotNull Value<Integer> left,@NotNull Value<Integer> right) {
        return greaterThan(left).and(lessThan(right));
    }

    public Condition equalTo(@NotNull Value<Integer> value) {
        return condition(Operator.EQ, this, value);
    }

    public Condition ne(@NotNull Value<Integer> value) {
        return condition(Operator.NE, this, value);
    }
}
