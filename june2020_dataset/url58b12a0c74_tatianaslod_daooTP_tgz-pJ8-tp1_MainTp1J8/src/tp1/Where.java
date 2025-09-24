package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

public class Where implements Visitable {
    @NotNull
    private final Condition condition;

    public Where(@NotNull Condition condition) {
        this.condition = condition;
    }

    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
        condition.accept(visitor);
    }

    public boolean isEmpty() {
        return condition.isEmpty();
    }

}
