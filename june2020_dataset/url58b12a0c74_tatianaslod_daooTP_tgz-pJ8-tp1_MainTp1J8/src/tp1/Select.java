package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

import java.util.List;

public class Select implements Visitable {
    @NotNull
    private final List<Column> selectColumns;

    public Select(@NotNull List<Column> selectColumns) {
        this.selectColumns = selectColumns;
    }

    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
    }

    public List<Column> getSelectColumns() {
        return selectColumns;
    }

    public boolean isEmpty() {
        return selectColumns.isEmpty();
    }
}
