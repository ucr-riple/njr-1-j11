package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

import java.util.List;

public class OrderBy implements Visitable {
    @NotNull
    private final List<Column> orderByColumns;

    public OrderBy(@NotNull List<Column> orderByColumns) {
        this.orderByColumns = orderByColumns;
    }

    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
   }

    public List<Column> getOrderByColumns() {
        return orderByColumns;
    }

    public boolean isEmpty() {
        return orderByColumns.isEmpty();
    }
}
