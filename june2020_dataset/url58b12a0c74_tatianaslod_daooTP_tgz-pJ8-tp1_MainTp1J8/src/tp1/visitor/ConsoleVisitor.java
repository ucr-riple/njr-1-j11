package tp1.visitor;

import com.sun.istack.internal.NotNull;
import tp1.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConsoleVisitor implements QueryVisitor {

    private String createSVFromList(@NotNull List<?> list, String first, String separator, String end) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(first);

        for (int i = 0; i < list.size() - 1; i++) {
            stringBuilder.append(list.get(i).toString());
            stringBuilder.append(separator);
        }
        if (!list.isEmpty()) {
            stringBuilder.append(list.get(list.size() - 1).toString());
        }
        stringBuilder.append(end);
        return stringBuilder.toString();
    }

    /**
     * Creates a String of Separated Values from a List of T Objects by a String separator
     *
     * @param list
     * @param separator
     * @return String
     */
    private String createSimpleSVFromList(@NotNull List<?> list, String separator) {
        return createSVFromList(list,"",separator,"");
    }


        @Override
    public void visit(@NotNull SqlQuery sqlQuery) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void visit(@NotNull Column column) {
        System.out.print(column.getName() + " ");
    }

    @Override
    public void visit(@NotNull Table table) {
        System.out.print(table.getName() + " ");
    }

    @Override
    public void visit(@NotNull Const constant) {
        System.out.print(constant.getValue().toString() + " ");

    }

    @Override
    public void visit(@NotNull Value value) {
        System.out.print(value.getValue().toString() + " ");

    }

    @Override
    public void visit(@NotNull Condition condition) {
        System.out.print(condition.getOperator() + " ");
    }

    @Override
    public void visit(@NotNull Select select) {
        System.out.print("\nSELECT ");
        if (select.isEmpty()) {
            System.out.print("*");
        } else {
            List<String> printableColumns = new LinkedList<String>();
            for (Column column : select.getSelectColumns()) {
                printableColumns.add(column.getName());
            }
            System.out.print(createSimpleSVFromList(printableColumns, ","));
        }
    }

    @Override
    public void visit(@NotNull From from) {
        System.out.print("\nFROM ");
        List<String> printableColumns = new LinkedList<String>();
        for (Table table : from.getFromTables()) {
            printableColumns.add(table.getName());
        }
        System.out.print(createSimpleSVFromList(printableColumns, ","));
    }

    @Override
    public void visit(@NotNull Where where) {
        if (!where.isEmpty()) {
            System.out.print("\nWHERE ");
        }
    }

    @Override
    public void visit(@NotNull GroupBy groupBy) {
        if (!groupBy.isEmpty()) {
            System.out.print("\nGROUPBY ");
            List<String> printableColumns = new LinkedList<String>();
            for (Column column : groupBy.getGroupByColumns()) {
                printableColumns.add(column.getName());
            }
            System.out.print(createSimpleSVFromList(printableColumns, ","));
        }
    }

    @Override
    public void visit(@NotNull OrderBy orderBy) {
        if (!orderBy.isEmpty()) {
            System.out.print("\nORDERBY ");
            List<String> printableColumns = new LinkedList<String>();
            for (Column column : orderBy.getOrderByColumns()) {
                printableColumns.add(column.getName());
            }
            System.out.print(createSimpleSVFromList(printableColumns, ","));
        }
    }

    @Override
    public void visit(@NotNull Limit limit) {
        if (!limit.isEmpty()) {
            System.out.print("\nLIMIT ");
            System.out.print(limit.getLimit());
        }
    }

    @Override
    public void visit(@NotNull Offset offset) {
        if (!offset.isEmpty()) {
            System.out.print("\nOFFSET ");
            System.out.print(offset.getOffset());
        }
    }

}
