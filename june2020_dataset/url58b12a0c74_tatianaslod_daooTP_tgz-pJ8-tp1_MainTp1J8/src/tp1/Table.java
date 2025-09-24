package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Table implements Visitable {
    @NotNull private final List<Column> columns;
    @NotNull private final String name;

    public Table(@NotNull List<Column> columns, @NotNull String name) {
        this.columns = columns;
        this.name = name;
    }
    public Table(String name) {
        this.columns = new LinkedList<Column>();
        this.name = name;
    }

    public StrColumn str(@NotNull String columnName) {
        if (!columns.isEmpty()) {
            for (Column column : columns) {
                if (column.getName().equals(columnName)) {
                    return (StrColumn) column;
                }
            }
        }
        StrColumn column = new StrColumn(columnName);
        columns.add(column);
        return column;
    }

    public IntColumn number(@NotNull String columnName) {
        if (!columns.isEmpty()) {
            for (Column column : columns) {
                if (column.getName().equals(columnName)) {
                    return (IntColumn) column;
                }
            }
        }
        IntColumn column = new IntColumn(columnName);
        columns.add(column);
        return column;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
    }

    public List<Column> getColumns() {
        return columns;
    }
}
