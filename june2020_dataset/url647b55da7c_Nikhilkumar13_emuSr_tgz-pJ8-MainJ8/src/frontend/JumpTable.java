/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author win-7
 */
public class JumpTable extends JPanel
{

    JumpTable()
      {
        label = new JLabel("Labels specified in the code");
        contain = new JScrollPane();
        jumpTable = new JTable();
        int range = 1000;
        Object[][] obj2 = new Object[range][2];

        for (int i = 0; i < range; i++)
        {

            obj2[i][0] = "    ";
            obj2[i][1] = "    ";
        }
        model2 = new MyTableModel(obj2, new String[]
                {
                    "Label Name", "Line No"
                });

        jumpTable.setModel(model2);

        contain.setViewportView(jumpTable);
        //     add(label,BorderLayout.PAGE_START);
        add(contain, BorderLayout.CENTER);
      }

   
    JLabel label;
    JScrollPane contain;
    JTable jumpTable;
    public static DefaultTableModel model2;
}

class MyTableModel2 extends javax.swing.table.DefaultTableModel
{

    public MyTableModel2(Object[][] tableData, Object[] colNames)
      {
        super(tableData, colNames);
      }

    @Override
    public boolean isCellEditable(int row, int column)
      {
        return false;
      }

    public int getRowCount()
      {
        return super.getRowCount();

      }
}
