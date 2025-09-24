package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: Personal
 * Date: 07/05/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class BinaryCondition extends Condition {
    @NotNull private final Statement<?> left;
    @NotNull private final Statement<?> right;
    @NotNull private final Operator operator;

    public BinaryCondition(@NotNull Operator operator,@NotNull  Statement<?> left,@NotNull Statement<?> right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void accept(@NotNull QueryVisitor visitor) {
        left.accept(visitor);
        visitor.visit(this);
        right.accept(visitor);
    }
}
