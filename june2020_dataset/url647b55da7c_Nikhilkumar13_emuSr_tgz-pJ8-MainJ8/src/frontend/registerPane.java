package frontend;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class registerPane extends MyPanel
{

    public registerPane()
      {
        GridBagLayout gbl = new GridBagLayout();

        setLayout(gbl);

        JLabel r0 = new JLabel("R0");
        JLabel r1 = new JLabel("R1");
        JLabel r2 = new JLabel("R2");
        JLabel r3 = new JLabel("R3");
        JLabel r4 = new JLabel("R4");
        JLabel r5 = new JLabel("R5");
        JLabel r6 = new JLabel("R6");
        JLabel r7 = new JLabel("R7");
        JLabel r8 = new JLabel("R8");
        JLabel r9 = new JLabel("R9");
        JLabel r10 = new JLabel("R10");
        JLabel r11 = new JLabel("R11");
        JLabel r12 = new JLabel("R12");
        JLabel r13 = new JLabel("R13");
        JLabel r14 = new JLabel("R14");
        JLabel r15 = new JLabel("R15");
        JLabel GT = new JLabel("FLAGS(GT)");
        JLabel E = new JLabel("FLAGS (E)");
        JLabel PC = new JLabel("PC");
        //JLabel v=new JLabel("V");
        //  BE = new JLabel("BE");

        r0_t = new MyTextField("");
        r1_t = new MyTextField("");
        r2_t = new MyTextField("");
        r3_t = new MyTextField("");
        r4_t = new MyTextField("");
        r5_t = new MyTextField("");
        r6_t = new MyTextField("");
        r7_t = new MyTextField("");
        r8_t = new MyTextField("");
        r9_t = new MyTextField("");
        r10_t = new MyTextField("");
        r11_t = new MyTextField("");
        r12_t = new MyTextField("");
        r13_t = new MyTextField("");
        r14_t = new MyTextField("");
        r15_t = new MyTextField("");
        //cpsr_t=new JTextField("");
        GT_t = new MyTextField("");
        E_t = new MyTextField("");
        PC_t = new MyTextField("");
        //v_t=new MyTextField("");
        //BE = new MyTextField("");

        r0_t.setEditable(false);
        r1_t.setEditable(false);
        r2_t.setEditable(false);
        r3_t.setEditable(false);
        r4_t.setEditable(false);
        r5_t.setEditable(false);
        r6_t.setEditable(false);
        r7_t.setEditable(false);
        r8_t.setEditable(false);
        r9_t.setEditable(false);
        r10_t.setEditable(false);
        r11_t.setEditable(false);
        r12_t.setEditable(false);
        r13_t.setEditable(false);
        r14_t.setEditable(false);
        r15_t.setEditable(false);

        GT_t.setEditable(false);
        E_t.setEditable(false);
        PC_t.setEditable(false);
        // v_t.setEditable(false);

        // gbc = makeGbc2(2, 0);
        GridBagConstraints gbc = makeGbc(0, 1);
        add(r0, gbc);
        gbc = makeGbc2(1, 1);
        add(r0_t, gbc);
        gbc = makeGbc(2, 1);
        add(r1, gbc);
        gbc = makeGbc2(3, 1);
        add(r1_t, gbc);
        gbc = makeGbc(0, 2);
        add(r2, gbc);
        gbc = makeGbc2(1, 2);
        add(r2_t, gbc);
        gbc = makeGbc(2, 2);
        add(r3, gbc);
        gbc = makeGbc2(3, 2);
        add(r3_t, gbc);
        gbc = makeGbc(0, 3);
        add(r4, gbc);
        gbc = makeGbc2(1, 3);
        add(r4_t, gbc);
        gbc = makeGbc(2, 3);
        add(r5, gbc);
        gbc = makeGbc2(3, 3);
        add(r5_t, gbc);
        gbc = makeGbc(0, 4);
        add(r6, gbc);
        gbc = makeGbc2(1, 4);
        add(r6_t, gbc);
        gbc = makeGbc(2, 4);
        add(r7, gbc);
        gbc = makeGbc2(3, 4);
        add(r7_t, gbc);
        gbc = makeGbc(0, 5);
        add(r8, gbc);
        gbc = makeGbc2(1, 5);
        add(r8_t, gbc);
        gbc = makeGbc(2, 5);
        add(r9, gbc);
        gbc = makeGbc2(3, 5);
        add(r9_t, gbc);
        gbc = makeGbc(0, 6);
        add(r10, gbc);
        gbc = makeGbc2(1, 6);
        add(r10_t, gbc);
        gbc = makeGbc(2, 6);
        add(r11, gbc);
        gbc = makeGbc2(3, 6);
        add(r11_t, gbc);
        gbc = makeGbc(0, 7);
        add(r12, gbc);
        gbc = makeGbc2(1, 7);
        add(r12_t, gbc);
        gbc = makeGbc(2, 7);
        add(r13, gbc);
        gbc = makeGbc2(3, 7);
        add(r13_t, gbc);
        gbc = makeGbc(0, 8);
        add(r14, gbc);
        gbc = makeGbc2(1, 8);
        add(r14_t, gbc);
        gbc = makeGbc(2, 8);
        add(r15, gbc);
        gbc = makeGbc2(3, 8);
        add(r15_t, gbc);
        gbc = makeGbc(0, 9);
        add(GT, gbc);
        gbc = makeGbc2(1, 9);
        add(GT_t, gbc);
        gbc = makeGbc(2, 9);
        add(E, gbc);
        gbc = makeGbc2(3, 9);
        add(E_t, gbc);
        gbc = makeGbc(0, 10);
        add(PC, gbc);
        gbc = makeGbc2(1, 10);
        add(PC_t, gbc);
        gbc = makeGbc(2, 10);
        //  add(v,gbc);
        // gbc = makeGbc2(3,10);
        // add(v_t,gbc);
      }

    private GridBagConstraints makeGbc(int x, int y)
      {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.ipadx = 0;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = (x == 0) ? GridBagConstraints.LINE_START : GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
      }

    private GridBagConstraints makeGbc2(int x, int y)
      {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = (x == 0) ? GridBagConstraints.LINE_START : GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
      }
//    private static void createAndShowUI() {
//        JFrame frame = new JFrame("Register Pane");
//        frame.getContentPane().add(new registerPane());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.pack();
//       
//       
//    }
//
//    public static void main(String[] args) {
//        java.awt.EventQueue.invokeLater(new Runnable()
//        {
//            @Override
//            public void run() 
//            {  
//                createAndShowUI();
//            }
//        });
//    }
    public static MyTextField r0_t;
    public static JTextField r1_t;
    public static JTextField r2_t;
    public static JTextField r3_t;
    public static JTextField r4_t;
    public static JTextField r5_t;
    public static JTextField r6_t;
    public static JTextField r7_t;
    public static JTextField r8_t;
    public static JTextField r9_t;
    public static JTextField r10_t;
    public static JTextField r11_t;
    public static JTextField r12_t;
    public static JTextField r13_t;
    public static JTextField r14_t;
    public static JTextField r15_t;
    //JTextField cpsr_t;
    public static JTextField GT_t;
    public static JTextField E_t;
    public static JTextField PC_t;
    //public static JTextField v_t;
}

class MyPanel extends JPanel
{

    public static JPopupMenu menu;
    handlers h;

    public MyPanel()
      {
        super();
        //System.out.println("\n %%%%%%%%%%%%%%");
        h = new handlers();
        addMouseListener(new PopupTriggerListener());
        menu = new javax.swing.JPopupMenu();
        JMenuItem lang1 = new JMenuItem("Decimal");
        lang1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 0;
                h.update(0);
              }
        });
        menu.add(lang1);
        JMenuItem lang2 = new JMenuItem("Binary");
        lang2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 1;
                h.update(1);
              }
        });
        menu.add(lang2);
        JMenuItem lang3 = new JMenuItem("Hexadecimal");
        lang3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 2;
                h.update(2);
              }
        });
        menu.add(lang3);
        JMenuItem lang4 = new JMenuItem("Octal");
        lang4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 3;
                h.update(3);
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

class MyTextField extends JTextField
{

    public static JPopupMenu menu;
    handlers h;

    public MyTextField(String t)
      {
        super(t);
        h = new handlers();
        addMouseListener(new PopupTriggerListener());
        menu = new javax.swing.JPopupMenu();
        JMenuItem lang1 = new JMenuItem("Decimal");
        lang1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 0;
                h.update(0);
              }
        });
        menu.add(lang1);
        JMenuItem lang2 = new JMenuItem("Binary");
        lang2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 1;
                h.update(1);
              }
        });
        menu.add(lang2);
        JMenuItem lang3 = new JMenuItem("Hexadecimal");
        lang3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 2;
                h.update(2);
              }
        });
        menu.add(lang3);
        JMenuItem lang4 = new JMenuItem("Octal");
        lang4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
              {
                handlers.regMode = 3;
                h.update(3);
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
