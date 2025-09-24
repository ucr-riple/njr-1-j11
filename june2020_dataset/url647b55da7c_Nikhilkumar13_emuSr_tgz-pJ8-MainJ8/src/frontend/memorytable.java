/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

/**
 *
 * @author Nikhil
 */
public class memorytable extends NP
{

    memorytable()
      {
        label = new JLabel("Memory Table");
        contain = new JScrollPane();
        jumpTable = new JTable();
        int range = 3000;
        Object[][] obj2 = new Object[range][2];

        for (int i = 0; i < range; i++)
        {

            obj2[i][0] = "    ";
            obj2[i][1] = "    ";
        }
        model2 = new MyTableModel(obj2, new String[]
                {
                    "Memory Location", "Memory Contents"
                });
        // model2 = new javax.swing.table.DefaultTableModel (obj2,new String [] {"Memory Location", "Memory Contents"});
        // boolean nk=model2.isCellEditable(2, 2);
        ////System.out.println("\n EDITABLE" +nk);
        // model2=new javax.swing.table.DefaultTableModel(obj2, obj2)

        jumpTable.setModel(model2);
        //jumpTable.setEnabled(false);

        contain.setViewportView(jumpTable);
        //add(label,BorderLayout.PAGE_START);
        add(contain, BorderLayout.CENTER);
      }
//    public static void main(String args[])
//    {
//        memorytable scrollpane=new memorytable();
//        JFrame frame = new JFrame("FileTreeDemo");
//    frame.getContentPane().add(scrollpane);
//   frame.setSize(400,600);
//    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);    
//    frame.setVisible(true);
//    }
}

class NP extends JPanel
{

    JLabel label;
    JScrollPane contain;
    JTable jumpTable;
    public static DefaultTableModel model2;
    public static JPopupMenu menu;
    handlers h;

    public NP()
      {
        super();
        //System.out.println("\n %%%%%%%%%%%%%%");
        h = new handlers();

        addMouseListener(new PopupTriggerListener());
        menu = new javax.swing.JPopupMenu();
        JMenuItem lang1 = new JMenuItem("Decimal");
        lang1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
              {
                handlers.update_memorytable(backend.Memory.getsize(), 0);
              }
        });
        menu.add(lang1);
        JMenuItem lang2 = new JMenuItem("Binary");
        lang2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.update_memorytable(backend.Memory.getsize(), 1);
              }
        });
        menu.add(lang2);
        JMenuItem lang3 = new JMenuItem("Hexadecimal");
        lang3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.update_memorytable(backend.Memory.getsize(), 2);
              }
        });
        menu.add(lang3);
        JMenuItem lang4 = new JMenuItem("Octal");
        lang4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.update_memorytable(backend.Memory.getsize(), 3);
              }
        });
        menu.add(lang4);

      }

    class PopupTriggerListener extends MouseAdapter
    {

        public void mousePressed(MouseEvent ev)
          {
            if (ev.isPopupTrigger())
            {
                menu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
          }

        public void mouseReleased(MouseEvent ev)
          {
            if (ev.isPopupTrigger())
            {
                menu.show(ev.getComponent(), ev.getX(), ev.getY());
            }
          }

        public void mouseClicked(MouseEvent ev)
          {
          }
    }
}

class MyTableModel extends javax.swing.table.DefaultTableModel
{

    public MyTableModel(Object[][] tableData, Object[] colNames)
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
