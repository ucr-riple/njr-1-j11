package tp1;

import org.jetbrains.annotations.NotNull;
import tp1.visitor.QueryVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: Personal
 * Date: 07/05/13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class UnitaryCondition extends Condition {

    @NotNull private final Operator operator;
    @NotNull private final Statement<?> left;

    public UnitaryCondition(@NotNull Operator operator,@NotNull  Statement<?> unary) {
        this.operator = operator;
        this.left = unary;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public Statement<?> getRight() {
        return left;
    }

    @Override
    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
        left.accept(visitor);
    }
}
