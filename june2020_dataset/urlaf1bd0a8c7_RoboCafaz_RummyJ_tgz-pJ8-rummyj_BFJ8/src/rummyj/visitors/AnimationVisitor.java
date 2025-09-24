package rummyj.visitors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;

import javax.swing.BoxLayout;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.Visitor;

public class AnimationVisitor extends JPanel implements Visitor
{
  private byte[]               array;
  private int                  pointer;
  private Map<Byte, Procedure> procedures;

  private JTable               table;
  private final int            x        = 25;
  private final int            y        = 1200;
  private final int            interval = 25;
  private String               windowName;

  public void visit(Left n)
  {
    pointer--;
    updateAnimation();
  }

  public void visit(Right n)
  {
    pointer++;
    updateAnimation();
  }

  public void visit(Increment n)
  {
    array[pointer]++;
    updateAnimation();
  }

  public void visit(Decrement n)
  {
    array[pointer]--;
    updateAnimation();
  }

  public void visit(Input n)
  {
    try
    {
      array[pointer] = (byte) System.in.read();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    updateAnimation();
  }

  public void visit(Output n)
  {
    System.out.print((char) array[pointer]);
    updateAnimation();
  }

  public void visit(Loop n)
  {
    while (array[pointer] != 0)
    {
      n.body.accept(this);
    }
    updateAnimation();
  }

  public void visit(Program n)
  {
    array = new byte[30000];
    pointer = 0;
    procedures = new HashMap<Byte, Procedure>();
    animate();
    n.body.accept(this);
  }

  public void visit(Message n)
  {
    for (int i = 0; i < n.character.length(); i++)
    {
      array[pointer] = (byte) n.character.charAt(i);
      pointer++;
      array[pointer] = 0;
      updateAnimation();
    }
  }

  public void visit(Procedure n)
  {
    procedures.put(Byte.parseByte("" + (array[pointer])), n);
    updateAnimation();
  }

  public void visit(ProcedureCall n)
  {
    Procedure procedure = procedures.get(Byte.parseByte("" + array[pointer]));
    if (procedure != null)
    {
      procedure.body.accept(this);
    }
    updateAnimation();
  }

  public void visit(Number number)
  {
    for (int i = 0; i < number.iterations; i++)
    {
      number.command.accept(this);
    }
    updateAnimation();
  }

  public AnimationVisitor(String windowName)
  {
    super();
    this.windowName = windowName;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    table = new JTable(new TableModel());
    table.setPreferredScrollableViewportSize(new Dimension(1280, 800));
    table.setFillsViewportHeight(true);
    table.setColumnSelectionAllowed(false);
    table.setRowSelectionAllowed(false);
    table.setCellSelectionEnabled(true);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JScrollPane scrollPane = new JScrollPane(table);

    add(scrollPane);
  }

  class TableModel extends AbstractTableModel
  {
    private boolean    colSet      = false;
    private String[]   columnNames = new String[x];
    private Object[][] data        = new Object[y][x];
    private int[]      highlighted = new int[2];

    public TableModel()
    {
      super();

      for (int i = 0; i < y; i++)
      {
        for (int j = 0; j < x; j++)
        {
          if (!colSet)
          {
            columnNames[j] = "" + j;
          }
          setValueAt("0", i, j);
        }
        colSet = true;
      }
    }

    public int getColumnCount()
    {
      return columnNames.length;
    }

    public int getRowCount()
    {
      return data.length;
    }

    public String getColumnName(int col)
    {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
      return data[row][col];
    }

    public Class getColumnClass(int c)
    {
      return getValueAt(0, c).getClass();
    }

    public void setValueAt(Object value, int row, int col)
    {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }

  private void createAndShowGUI()
  {
    JFrame frame = new JFrame(windowName);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setOpaque(true);
    frame.setContentPane(this);

    frame.pack();
    frame.setVisible(true);
  }

  public void animate()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowGUI();
      }
    });
  }

  public void updateAnimation()
  {
    int j = pointer % x;
    int i = pointer - j;
    table.getModel().setValueAt(array[pointer], i, j);
    table.getSelectionModel().setSelectionInterval(i, i);
    table.getColumnModel().getSelectionModel().setSelectionInterval(j, j);
    try
    {
      Thread.sleep(interval);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}