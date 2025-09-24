package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

import java.util.List;


public class SqlQuery implements Visitable {
    @NotNull
    private final Select select;
    @NotNull
    private final From from;
    @NotNull
    private final Where where;
    @NotNull
    private final OrderBy orderBy;
    @NotNull
    private final GroupBy groupBy;
    @NotNull
    private final Limit limit;
    @NotNull
    private final Offset offset;

    SqlQuery(@NotNull List<Column> selectColumns, @NotNull List<Table> fromTables, @NotNull Condition condition, @NotNull List<Column> orderByColumns, @NotNull List<Column> groupByColumns, int limit) {
        this.select = new Select(selectColumns);
        this.from = new From(fromTables);
        this.where = new Where(condition);
        this.orderBy = new OrderBy(orderByColumns);
        this.groupBy = new GroupBy(groupByColumns);
        this.limit = new Limit(limit);
        this.offset = new Offset(0);

    }

    @Override
    public void accept(QueryVisitor visitor) {
        visitor.visit(this);
        select.accept(visitor);
        from.accept(visitor);
        where.accept(visitor);
        orderBy.accept(visitor);
        groupBy.accept(visitor);
        limit.accept(visitor);
        offset.accept(visitor);
    }

}
