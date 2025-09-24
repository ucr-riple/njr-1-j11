package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.engine.IDataBase;
import ru.spbau.tinydb.expressions.AssignmentExpression;

import java.util.Collections;
import java.util.List;

/**
 * @author adkozlov
 */
public class UpdateTableQuery extends TableNameContainer implements IQuery<Integer> {

    @NotNull
    private final List<AssignmentExpression> expressions;
    @NotNull
    private final WhereCondition filter;

    public UpdateTableQuery(@NotNull String tableName, @NotNull List<AssignmentExpression> expressions, @NotNull WhereCondition filter) {
        super(tableName);
        this.expressions = Collections.unmodifiableList(expressions);
        this.filter = filter;
    }

    @NotNull
    public List<AssignmentExpression> getExpressions() {
        return expressions;
    }

    @NotNull
    public WhereCondition getFilter() {
        return filter;
    }

    @NotNull
    @Override
    public Integer execute(@NotNull IDataBase instance) throws DBException {
        throw new DBException(new UnsupportedOperationException("unsupported update table operation"));
    }

    @NotNull
    @Override
    public String toString() {
        return "UpdateTableQuery{" +
                "expressions=" + expressions +
                ", filter=" + filter +
                "} " + super.toString();
    }
}
