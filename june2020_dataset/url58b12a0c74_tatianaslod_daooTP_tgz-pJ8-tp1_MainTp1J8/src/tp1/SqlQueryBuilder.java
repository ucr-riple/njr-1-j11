package tp1;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tp1.visitor.QueryVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class SqlQueryBuilder {
    @NotNull private List<Column> selectColumns;
    @NotNull private List<Table> fromTables;
    @NotNull private Condition whereClause;
    @NotNull private List<Column> orderByColumns;
    @NotNull private List<Column> groupByColumns;
    @NotNull private int limit;

    private SqlQueryBuilder() {
        orderByColumns = Collections.emptyList();
        fromTables = Collections.emptyList();
        groupByColumns = Collections.emptyList();
        selectColumns = new ArrayList<>();
        whereClause = new EmptyCondition();
    }

    public static SqlQueryBuilder sqlQuery() {
        return new SqlQueryBuilder();
    }

    @NotNull
    public SqlQueryBuilder select(@Nullable final Column... columns) {
        selectColumns = asList(columns);
        return this;
    }

    @NotNull
    public SqlQueryBuilder from(@Nullable final Table... tables) {
        fromTables = asList(tables);
        return this;
    }

    @NotNull
    public SqlQueryBuilder where(@NotNull final Condition condition) {
        whereClause = condition;
        return this;
    }

    @NotNull
    public SqlQueryBuilder orderBy(@Nullable final Column... columns) {
        orderByColumns = asList(columns);
        return this;
    }

    @NotNull
    public SqlQueryBuilder groupBy(@Nullable final Column... columns) {
        groupByColumns = asList(columns);
        return this;
    }

    @NotNull
    public SqlQueryBuilder limit(int limit) {
        if (limit < 0)
            throw new NumberFormatException();
        else {
            this.limit = limit;
            return this;
        }
    }

    public SqlQuery build() throws Exception {
        //todo> validation
        return  new SqlQuery(selectColumns, fromTables, whereClause, orderByColumns, groupByColumns, limit );
    }

}
