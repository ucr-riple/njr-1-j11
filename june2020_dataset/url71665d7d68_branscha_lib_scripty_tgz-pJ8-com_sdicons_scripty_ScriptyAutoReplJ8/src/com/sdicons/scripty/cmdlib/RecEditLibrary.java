/*
 * Scripty Programming Language
 * Copyright (C) 2010-2012 Bruno Ranschaert, S.D.I.-Consulting BVBA
 * http://www.sdi-consulting.be
 * mailto://info@sdi-consulting.be
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.sdicons.scripty.cmdlib;

import com.sdicons.scripty.parser.CommandException;
import com.sdicons.scripty.annot.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

@ScriptyLibrary(type=ScriptyLibraryType.STATIC)
public class RecEditLibrary
{
    @ScriptyCommand(name="rec-edit")
    @ScriptyStdArgList(fixed={@ScriptyArg(name="fields", type ="ListOf(String)"), @ScriptyArg(name="values", type="ListOf(Any)")})
    public static List recEdit(@ScriptyParam("fields") List<String> aPropNames, @ScriptyParam("values") List<Object> aPropVals)
    throws CommandException
    {
        if(aPropNames.size() != aPropVals.size()) throw new CommandException("Nr. of property names and values is different.");

        String[] lPropNamesArr = new String[aPropNames.size()];
        Object[] lPropValArr = new Object[aPropVals.size()];

        for(int i = 0; i < lPropNamesArr.length; i++) lPropNamesArr[i] = (String) aPropNames.get(i);
        for(int i = 0; i < lPropValArr.length; i++) lPropValArr[i] = aPropVals.get(i);

        PropertyPage lPage = new PropertyPage(lPropNamesArr, lPropValArr);
        int result = JOptionPane.showOptionDialog(null, lPage, "Property Editor",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.PLAIN_MESSAGE, null, null, null);
        return result==JOptionPane.OK_OPTION?lPage.getValues():null;
    }
}

class RowEditorModel
{
    @SuppressWarnings("unchecked")
    private Hashtable data;

    public RowEditorModel()
    {
        data = new Hashtable();
    }

    @SuppressWarnings("unchecked")
    public void addEditorForRow(int row, TableCellEditor e)
    {
        data.put(new Integer(row), e);
    }

    public void removeEditorForRow(int row)
    {
        data.remove(new Integer(row));
    }

    public TableCellEditor getEditor(int row)
    {
        return (TableCellEditor) data.get(new Integer(row));
    }
}

@SuppressWarnings("serial")
class JPropTable extends JTable
{
    protected RowEditorModel rm;

    public JPropTable()
    {
        super();
        rm = null;
    }

    public JPropTable(TableModel tm)
    {
        super(tm);
        rm = null;
    }

    public JPropTable(TableModel tm, TableColumnModel cm)
    {
        super(tm,cm);
        rm = null;
    }

    public JPropTable(TableModel tm, TableColumnModel cm,
                      ListSelectionModel sm)
    {
        super(tm,cm,sm);
        rm = null;
    }

    public JPropTable(int rows, int cols)
    {
        super(rows,cols);
        rm = null;
    }

    public JPropTable(final Vector rowData, final Vector columnNames)
    {
        super(rowData, columnNames);
        rm = null;
    }

    public JPropTable(final Object[][] rowData, final Object[] colNames)
    {
        super(rowData, colNames);
        rm = null;
    }

    // new constructor
    public JPropTable(TableModel tm, RowEditorModel rm)
    {
        super(tm,null,null);
        this.rm = rm;
    }

    public void setRowEditorModel(RowEditorModel rm)
    {
        this.rm = rm;
    }

    public RowEditorModel getRowEditorModel()
    {
        return rm;
    }

    public TableCellEditor getCellEditor(int row, int col)
    {
        if (rm != null)
        {
            TableCellEditor tmpEditor = rm.getEditor(row);
            if (tmpEditor != null) return tmpEditor;
        }
        return super.getCellEditor(row,col);
    }

    public void changeSelection(int row, int col, boolean toggle, boolean expand)
    {
        // This method is called when the user tries to move to a diffferent
        // cell.
        // If the cell they're trying to move to is not editable, we look for
        // then next cell in the proper direction that is editable.
        if (!getModel().isCellEditable(row, col))
        {
            // Find the row and column we're coming from.
            int curRow = getEditingRow();
            int curCol = getEditingColumn();
            if (curRow == -1) curRow = getSelectedRow();
            if (curCol == -1) curCol = getSelectedColumn();

            // We may need to wrap-around.
            int nRows = getRowCount();
            int nCols = getColumnCount();

            // If we can't find a cell to move to, we'll stay here.
            int nextRow = row;
            int nextCol = col;

            if (col == curCol)
            {
                // Up or down motion - go only up or down.
                int direction = row - curRow;
                if (direction > 1) direction = 1;
                if (direction < -1) direction = -1;
                nextRow = findNextEditableRow(row, col, direction, nRows, nCols);
            }
            else if (row == curRow)
            {
                // Left-or-right motion - use the "natural" (for Americans)
                // order:
                // left-to-right, top-to-bottom, or vice-versa if we're trying
                // to move to the left. We'll wrap from the bottom row to the
                // top
                // and vice-versa if necessary.
                int direction = col - curCol;
                if (direction > 1) direction = 1;
                if (direction < -1) direction = -1;
                int[] nextCell = findNextEditableCell(row, col, direction, nRows, nCols);
                nextRow = nextCell[0];
                nextCol = nextCell[1];
            }
            else
            {
                // Both row and column differ. This probably means we've
                // moved off the end of a row, or else the user has clicked
                // on some random cell. The direction is controlled
                // by the row difference (this doesn't always do something
                // intuitive; always setting direction=1 might work better).
                int direction = row - curRow;
                if (direction > 1) direction = 1;
                if (direction < -1) direction = -1;
                if ((row == 0) && (curRow == nRows - 1)) direction = 1;
                int[] nextCell = findNextEditableCell(row, col, direction, nRows, nCols);
                nextRow = nextCell[0];
                nextCol = nextCell[1];
            }
            // Go to the cell we found.
            super.changeSelection(nextRow, nextCol, toggle, expand);
        }
        else
        {
            // It's an editable cell, so leave the selection here.
            super.changeSelection(row, col, toggle, expand);
        }
    }

    // Search for an editable cell starting at row,col and using the
    // "natural" order.
    int[] findNextEditableCell(int row, int col, int direction, int nRows, int nCols)
    {
        int origRow = row;
        int origCol = col;
        do
        {
            col = col + direction;
            if (col >= nCols)
            {
                col = 0;
                row += direction;
            }
            if (col < 0)
            {
                col = nCols - 1;
                row += direction;
            }
            if (row >= nRows) row = 0;
            if (row < 0) row = nRows - 1;
            if (isCellEditable(row, col)) return new int[] { row, col };
        }
        while (!((row == origRow) && (col == origCol)));

        // Nothing editable found; stay here.
        return new int[] { origRow, origCol };
    }

    // Search directly above/below for an editable cell.
    int findNextEditableRow(int row, int col, int direction, int nRows, int nCols)
    {
        int origRow = row;
        do
        {
            row = row + direction;
            if (row < 0) row = nRows - 1;
            if (row >= nRows) row = 0;
            if (isCellEditable(row, col)) return row;
        }
        while (row != origRow);
        // Nothing editable found, stay here.
        return origRow;
    }
}

@SuppressWarnings("serial")
class PropertyPage
        extends JPanel
{
    private JPropTable table;
    private DefaultTableModel model;
    private String[] colNames = {"Name", "Value"};
    private int nrProps;

    public PropertyPage(final String[] aPropNames, Object[] aPropValues)
    {
        setLayout(new BorderLayout());
        nrProps = aPropNames.length;

        model = new DefaultTableModel(colNames, aPropNames.length)
        {
            public String[] propNames = aPropNames;

            public Object getValueAt(int row, int col)
            {
                if (col==0)return propNames[row];
                return super.getValueAt(row,col);
            }

            public boolean isCellEditable(int row, int col)
            {
                return col != 0;
            }
        };

        table = new JPropTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        //table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);

        for(int i = 0; i  < aPropValues.length; i++)
            table.setValueAt(aPropValues[i], i, 1);

        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setGridColor(Color.GRAY);

        // create a RowEditorModel... this is used to hold the extra
        // information that is needed to deal with row specific editors
        RowEditorModel rm = new RowEditorModel();
        table.setRowEditorModel(rm);

        // EXAMPLE.
        // Create a new JComboBox and DefaultCellEditor to use in the JTableX column
        //////////////////////////////////////////////////////////
        // JComboBox cb = new JComboBox(anchor_values);
        // DefaultCellEditor ed = new DefaultCellEditor(cb);
        // Tell the RowEditorModel to use ed for row 1.
        //rm.addEditorForRow(1,ed);
    }

    public List<Object> getValues()
    {
        List<Object> lResult = new LinkedList<Object>();
        for(int i = 0; i < nrProps; i++) lResult.add(table.getValueAt(i, 1));
        return lResult;
    }
}