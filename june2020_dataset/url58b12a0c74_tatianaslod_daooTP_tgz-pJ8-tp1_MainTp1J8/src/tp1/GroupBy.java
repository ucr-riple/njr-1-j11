package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;
import java.util.List;

public class GroupBy implements Visitable {
    @NotNull
    private final List<Column> groupByColumns;

    public GroupBy(@NotNull List<Column> groupByColumns) {
        this.groupByColumns = groupByColumns;
    }

    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
    }

    public List<Column> getGroupByColumns() {
        return groupByColumns;
    }

    public boolean isEmpty() {
        return groupByColumns.isEmpty();
    }
}
