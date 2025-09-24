/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen.view;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import nextgen.model.Entity;

/**
 *
 * @author Rodrigo
 */
public class NextGenHelper {

    public static Entity[] primitives = new Entity[]{
        new Entity("Integer", ""),
        new Entity("String", ""),
        new Entity("Float", ""),
        new Entity("Double", ""),
        new Entity("Boolean", "")
    };
    public static void adjustColumns(JTable table) {
        // strategy - get max width for cells in column and
        // make that the preferred width
        TableColumnModel columnModel = table.getColumnModel();
        for (int col = 0; col < table.getColumnCount(); col++) {

            int maxwidth = 0;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer rend =
                        table.getCellRenderer(row, col);
                Object value = table.getValueAt(row, col);
                Component comp =
                        rend.getTableCellRendererComponent(table,
                        value,
                        false,
                        false,
                        row,
                        col);
                maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
            } // for selectedRow
            TableColumn column = columnModel.getColumn(col);
            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = table.getTableHeader().getDefaultRenderer();
            }
            Object headerValue = column.getHeaderValue();
            Component headerComp =
                    headerRenderer.getTableCellRendererComponent(table,
                    headerValue,
                    false,
                    false,
                    0,
                    col);
            maxwidth = Math.max(maxwidth,
                    headerComp.getPreferredSize().width);
            maxwidth = Math.min(maxwidth, 200);
            column.setPreferredWidth(maxwidth);

        } // for col
    }
}
