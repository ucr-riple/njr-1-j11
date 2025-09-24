/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;
import java.awt.Color;

import backend.Register;
import backend.ScanFile;
import backend.StepRun;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.JToggleButton;
import javax.swing.JTextPane;

import java.awt.event.*;
import javax.swing.KeyStroke;



import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.*;
/**
 *
 * @author Tushar
 */
public class FrontEnd extends javax.swing.JFrame {

    static String filepath;
    public static Clipboard clip;
    public static int exceptionraised = 0;
    Dimension dimension;
    //*********main window variables***************
    public static JTabbedPane check;
    checkpoint c;
    public static JTabbedPane EditorPane;
    public JTabbedPane SidePane;
    public static JTextPane statuswindow;
    public JScrollPane checkpointsContainer;
    public JScrollPane EditorContainer;
    public static JEditorPane activepane;
    public JScrollPane RegisterContainer;
    public JScrollPane StatusContainer;
    public JScrollPane container;
    public JSplitPane checkpoints;
    public JSplitPane registerPaneAndEditor;
    public JSplitPane OutputAndPanels;
    //*********menu bar variables***************
    JMenuBar menu;
    JMenu file;
    JMenu edit;
    JMenu run;
    JMenu help;
    
    JMenuItem file_new;
//    JMenuItem file_close_current;
    JMenuItem file_open;
    JMenuItem file_save;
    JMenuItem file_exit;
    JMenuItem edit_cut;
    JMenuItem edit_copy;
    JMenuItem edit_paste;
    JMenuItem run_runFile;
    JMenuItem run_stepInto;
    public static JMenuItem run_Next;
    public static JMenuItem run_stepOut;
    public static JMenuItem run_stepOver;
    public static JMenuItem reset;
    public static JMenuItem Shortcut;
    JMenuItem help_about;
    JMenuItem help_contact;
    JMenuItem help_report;
    JMenuItem Features;
    //*************toolbar variables
    JToolBar toolbar;
    JButton f_new;
    JButton f_open;
    JButton f_save;
    JButton f_exit;
    JButton e_cut;
    JButton e_copy;
    JButton e_paste;
    public static JButton r_runFile;
    JButton h_about;
    JButton h_contact;
    JButton h_report;
    public static JToggleButton onconsole;
    //*********** stepinto toolbar variables
    public static JButton r_stepInto;
    public static javax.swing.JButton steprun;
    public static JButton r_stepOut;
    public static JButton r_stepover;
    public static javax.swing.JLabel warning;
    public static JButton cross;
    public static JButton stop_debug_mode;
    public static JButton clr;
    public static JButton clr2;
    //**************************************
    handlers h;
  
    //**************************************

    public FrontEnd() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        h = new handlers();
        this.setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        this.setTitle("emu Simple Risc V0.1");
        filepath = null;
       // this.setLocationRelativeTo(null);//addedby nikhil
        clip = getToolkit().getSystemClipboard();
        initializeScreen();

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                
                setDividerLocation(registerPaneAndEditor, 0.7);
                checkpoints.resetToPreferredSizes();
                OutputAndPanels.resetToPreferredSizes();
                registerPaneAndEditor.resetToPreferredSizes();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("\nExiting.....Made By Nikhil and Tushar  CSE@IITD \nFeel free to share your thoughts about emuSimple Risc at nikkr007@gmail.com \n..GoodBye");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    
    private void initializeScreen() {

        check = new JTabbedPane();
        check.setMinimumSize(new Dimension(0, 0));
        check.setMaximumSize(new Dimension(1, 1));

        EditorPane = new JTabbedPane();
        EditorPane.setMinimumSize(new Dimension(0, 0));
        EditorPane.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                h.EditorStateChanged(evt);
            }
        });

        SidePane = new JTabbedPane();
        SidePane.setMinimumSize(new Dimension(0, 0));


        statuswindow = new JTextPane();
        statuswindow.setEditable(false);
//        frontend.FrontEnd.statuswindow.addKeyListener(new KeyAdapter() {
//            private StringBuffer line = new StringBuffer();
//
//            @Override
//            public void keyTyped(KeyEvent e) {
//                char c = e.getKeyChar();
////                line.append(c);
//                if (c == KeyEvent.VK_ENTER) {
//                    backend.ScanFile.ob.setinput(line);
//                    line.setLength(0);
//                } else if (c == KeyEvent.VK_BACK_SPACE) {
//                    line.setLength(line.length() - 1);
//                } else if (!Character.isISOControl(c)) {
//                    line.append(e.getKeyChar());
//                }
//            }
//        });


        EditorContainer = new JScrollPane(EditorPane);
        EditorContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        EditorContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        EditorContainer.setPreferredSize(new Dimension(dimension.width / 30, dimension.height / 3));

        checkpointsContainer = new JScrollPane(check);
        checkpointsContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        checkpointsContainer.setPreferredSize(new Dimension(dimension.width / 30, dimension.height / 3));

        RegisterContainer = new JScrollPane(SidePane);
        RegisterContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        RegisterContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        RegisterContainer.setPreferredSize(new Dimension(dimension.width / 4, (2 * dimension.height) / 3));

        StatusContainer = new JScrollPane(statuswindow);
        StatusContainer.setPreferredSize(new Dimension(dimension.width - 50, (dimension.height) / 8));
        StatusContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        StatusContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        registerPaneAndEditor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, EditorContainer, RegisterContainer);
        registerPaneAndEditor.setResizeWeight(0.7);
        registerPaneAndEditor.setOneTouchExpandable(true);

        checkpoints = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, checkpointsContainer, registerPaneAndEditor);
        checkpoints.setResizeWeight(0.00000005);
        checkpoints.setOneTouchExpandable(true);
        checkpoints.setMinimumSize(new Dimension(1, 10));
        checkpoints.setMaximumSize(new Dimension(1, 10));

        OutputAndPanels = new JSplitPane(JSplitPane.VERTICAL_SPLIT, checkpoints, StatusContainer);
        OutputAndPanels.setResizeWeight(0.8);
        OutputAndPanels.setOneTouchExpandable(true);

        container = new JScrollPane(OutputAndPanels);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        initializeToolBar();
        initializecheckpoints();
        intializeSidePane();
        initializeEditorPane();

        this.add(container, BorderLayout.CENTER);
        initializeMenuBar();
        this.pack();
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    void initializeMenuBar() {

        menu = new JMenuBar();
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));

        file = new JMenu("File");
        file.setMnemonic(java.awt.event.KeyEvent.VK_F);

        file_new = new JMenuItem();
        file_new.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        file_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/new.png"))); // NOI18N
        file_new.setText("New");
        file_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.newFile();
            }
        });
        file.add(file_new);

        file_open = new javax.swing.JMenuItem();
        file_open.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        file_open.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349523_folder_add.png"))); // NOI18N
        file_open.setText("Open");
        file_open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.openfile();
            }
        });
        file.add(file_open);

        file_save = new javax.swing.JMenuItem();
        file_save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        file_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349552_diskette.png"))); // NOI18N
        file_save.setText("Save");
        file_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.savefile();
            }
        });
        file.add(file_save);

        file_exit = new javax.swing.JMenuItem();
        file_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        file_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/exit.png"))); // NOI18N
        file_exit.setText("Exit");
        file_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.exit();
            }
        });
        file.add(file_exit);

        menu.add(file);

        edit = new JMenu("Edit");
        edit.setMnemonic(java.awt.event.KeyEvent.VK_E);

        edit_cut = new javax.swing.JMenuItem();
        edit_cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        edit_cut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349801_Cut.png"))); // NOI18N
        edit_cut.setText("Cut");
        edit_cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    h.cut();
                } catch (BadLocationException ex) {
                    Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        edit.add(edit_cut);

        edit_copy = new javax.swing.JMenuItem();
        edit_copy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        edit_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349791_Copy.png"))); // NOI18N
        edit_copy.setText("Copy");
        edit_copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.copy();
            }
        });
        edit.add(edit_copy);

        edit_paste = new javax.swing.JMenuItem();
        edit_paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        edit_paste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349865_edit-paste.png"))); // NOI18N
        edit_paste.setMnemonic('V');
        edit_paste.setText("Paste");
        edit_paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.paste();
            }
        });
        edit.add(edit_paste);

        menu.add(edit);

        run = new JMenu("Run");
       // run.setMnemonic(java.awt.event.KeyEvent.VK_R);
       // run.setMnemonic(java.awt.Event.CTRL_MASK);

        run_runFile = new javax.swing.JMenuItem();
       // run_runFile.setAccelerator(javax.swing.KeyStroke.get
        run_runFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1,0));
        run_runFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349207_player_play.png"))); // NOI18N
        run_runFile.setText("Run Code"); 
        run_runFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.run();
            }
        });
        run.add(run_runFile);
        
        run_stepInto = new javax.swing.JMenuItem();
       // run_stepInto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2,0));
        run_stepInto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/StepInto.png"))); // NOI18N
        run_stepInto.setText("Step Into");
        run_stepInto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Mode Activated \n",Color.BLACK);

                h.stepinto();
            }
        });

        run.add(run_stepInto);
         run_Next = new javax.swing.JMenuItem();
                 run_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/Next.png"))); // NOI18N

        run_Next.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2,0));//java.awt.Event.CTRL_MASK));
       // run_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/StepInto.png"))); // NOI18N
       run_Next.setEnabled(false);
       
        
        run_Next.setText("Next");
        //run_Next.setVisible(false);
        run_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    h.steprun2();
                   // System.out.println("45665467865467976765577897");
                } catch (IOException ex)
                {
                    Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        run.add(run_Next);

        run_stepOut = new javax.swing.JMenuItem();
       // run_stepOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8,0));
        run_stepOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/stepout.png"))); // NOI18N
        run_stepOut.setText("Step Out");
        run_stepOut.setEnabled(false);
        run_stepOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Mode Step Out Activated\n",Color.BLACK);

                
                h.stepout();
            }
        });
        run.add(run_stepOut);

        run_stepOver = new javax.swing.JMenuItem();
       // run_stepOver.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        run_stepOver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/stepover.png"))); // NOI18N
        run_stepOver.setText("Step Over");
        run_stepOver.setEnabled(false);
        run_stepOver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Mode Step Over Activated\n",Color.BLACK);

                h.stepover();
            }
        });
        run.add(run_stepOver);

        reset = new javax.swing.JMenuItem();
        //reset.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/refresh.png"))); // NOI18N
        reset.setText("Reset");
        reset.setEnabled(false);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkpoint.reset();
            }
        });
        run.add(reset);
        Shortcut = new javax.swing.JMenuItem();
        Shortcut.setText("Shortcut Keys");
       Shortcut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/shrt.jpg")));
        Shortcut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "F1  Run Code\nF2  Next \nCtrl+N  New Blank Tab\nCtrl+O  Open File\nCtrl+S  Save \nCtrl+A  Select All \nCtrl+X  Cut\nCtrl+C  Copy\nCtrl+V  Paste\nCtrl+Q  Quit","Shortcut Keys",JOptionPane.INFORMATION_MESSAGE);
//           JOptionPane.showMe
            }
        });
        run.add(Shortcut);
        
        menu.add(run);

        help = new JMenu("Help");
        help.setMnemonic(java.awt.event.KeyEvent.VK_H);
        Features = new javax.swing.JMenuItem();
        Features.setText("Key Features");
       Features.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/key.jpg")));
        Features.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "1. Support all 21 Risc Instructions and also .print Instruction\n2. Immediates can be given in Decmal and Hex (preciding with 0x)\n3. Clearly Highlights the bug in Code with Red\n4. Supports Multiline Comments /*...*/,Single Line with //and @.\n5. You can Use Step Run, Step Out, Step Over, for finding bug in your code.\n6. ShortcutKeys are availabe for Butons such as Run, Next...\n7. It can take one Label and Instruction in same line and also you can give comments bettwen them if you wish.\n8. Value stored in Register and Memory can be seen  in 4 formats (Dec,Hex,Oct,Binary).You Just Need to Click on Register Pane and select your choice.\n  ","Key Features",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(Features);

        help_about = new javax.swing.JMenuItem();
        help_about.setText("About Us");
        help_about.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/about.png"))); // NOI18N
        help_about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "This is a Java based Simple Risc emulator machine.\nVersion 0.1\nDeveloped By:\nNikhil Kumar\nTushar Marda\nIndian Institute of Technology, New Delhi-16","About Us",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(help_about);

        help_contact = new javax.swing.JMenuItem();
        help_contact.setText("Contact Us");
        //help_contact.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        help_contact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/contact.png")));
        help_contact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Feel free to contact me at nikkr007@gmail.com","Contact Us",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(help_contact);

        help_report = new javax.swing.JMenuItem();
        help_report.setText("Report a bug");
        help_report.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/bug.png")));
        help_report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Thankyou for your report. Please e-mail the details to nikkr007@gmail.com","Report Bug",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(help_report);

        menu.add(help);
        

        menu.add(help);

        this.setJMenuBar(menu);
    }

    void initializeToolBar() {
        toolbar = new JToolBar();




        f_new = new JButton();
        f_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/new.png"))); // NOI18N
        f_new.setToolTipText("New File");
        f_new.setBorderPainted(false);
        f_new.setFocusable(false);
        f_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.newFile();
            }
        });
        toolbar.add(f_new);

        f_open = new JButton();
        f_open.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349523_folder_add.png"))); // NOI18N
        f_open.setToolTipText("Open File");
        f_open.setFocusable(false);
        f_open.setBorderPainted(false);
        f_open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.openfile();
            }
        });
        toolbar.add(f_open);

        f_save = new JButton();
        f_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349552_diskette.png"))); // NOI18N
        f_save.setToolTipText("Save File");
        f_save.setFocusable(false);
        f_save.setBorderPainted(false);
        f_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EditorPane.getTabCount() > 0) {
                    h.savefile();
                } else {
                    appendToPane(statuswindow,"\n Nothing to save",Color.BLACK);
                    statuswindow.setCaretPosition(statuswindow.getText().length());
                }
            }
        });
        toolbar.add(f_save);

        f_exit = new JButton();
        f_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/exit.png"))); // NOI18N
        f_exit.setToolTipText("Exit");
        f_exit.setFocusable(false);
        f_exit.setBorderPainted(false);
        f_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.exit();
            }
        });
        toolbar.add(f_exit);


        e_cut = new JButton();
        e_cut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349801_Cut.png"))); // NOI18N
        e_cut.setToolTipText("Cut");
        e_cut.setFocusable(false);
        e_cut.setBorderPainted(false);
        e_cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    h.cut();
                } catch (BadLocationException ex) {
                    Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        toolbar.add(e_cut);

        e_copy = new JButton();
        e_copy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349791_Copy.png"))); // NOI18N
        e_copy.setToolTipText("Copy");
        e_copy.setFocusable(false);
        e_copy.setBorderPainted(false);
        e_copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.copy();
            }
        });
        toolbar.add(e_copy);

        e_paste = new JButton();
        e_paste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349865_edit-paste.png"))); // NOI18N
        e_paste.setToolTipText("Paste");
        e_paste.setFocusable(false);
        e_paste.setBorderPainted(false);
        e_paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.paste();
            }
        });
        toolbar.add(e_paste);



        r_runFile = new JButton();
        r_runFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/1309349207_player_play.png"))); // NOI18N
        r_runFile.setToolTipText("Run File");
        // r_runFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));

        r_runFile.setFocusable(false);
        r_runFile.setBorderPainted(false);
        r_runFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.run();
            }
        });
        toolbar.add(r_runFile);

        r_stepInto = new JButton();
        r_stepInto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/StepInto.png"))); // NOI18N
        r_stepInto.setToolTipText("Step Into ");
        r_stepInto.setFocusable(false);
        r_stepInto.setBorderPainted(false);
        r_stepInto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Mode Activated \n",Color.BLACK);
                h.stepinto();
            }
        });
        toolbar.add(r_stepInto);
        
        r_stepOut = new JButton();
        r_stepOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/stepout.png"))); // NOI18N
        r_stepOut.setToolTipText("Step Out ");
        r_stepOut.setFocusable(false);
        r_stepOut.setBorderPainted(false);
        r_stepOut.setEnabled(false);
        r_stepOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScanFile.call_count > 0) {
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Mode Step Out Activated\n",Color.BLACK);

                    h.stepout();
                }
            }
        });
        toolbar.add(r_stepOut);

        r_stepover = new JButton();
        r_stepover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/stepover.png"))); // NOI18N
        r_stepover.setToolTipText("Step Over");
        r_stepover.setFocusable(false);
      
        r_stepover.setBorderPainted(false);
        r_stepover.setEnabled(false);
        r_stepover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Mode Step Over Activated\n",Color.BLACK);
                h.stepover();
            }
        });
        toolbar.add(r_stepover);



        JLabel junk = new JLabel("                ");
        toolbar.add(junk);
        steprun = new JButton();
        steprun.setText("Next ");
       // steprun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10,0));

        steprun.setVisible(false);
       // Keystroke k= new Keystroke();
      //  steprun.getActionForKeyStroke(java.awt.event.InputEvent.CTRL_MASK);
        steprun.getActionForKeyStroke(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3,java.awt.event.InputEvent.CTRL_MASK));//getInputMap().get());
        steprun.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    //FrontEnd.appendToPane(FrontEnd.statuswindow," \n",Color.BLACK);
                    h.steprunActionPerformed(evt);
                } catch (IOException ex) {
                }
            }
        });
        toolbar.add(steprun);


        warning = new JLabel();
        warning.setForeground(new java.awt.Color(255, 0, 0));
        warning.setText("**End of File Reached");
        warning.setVisible(false);
        toolbar.add(warning);

        cross = new JButton();
        cross.setText("X");
        cross.setVisible(false);
        cross.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Completed \n",Color.BLACK);
                steprun.setEnabled(true);
                steprun.setVisible(false);
                stop_debug_mode.setEnabled(true);
                stop_debug_mode.setVisible(false);
                Register.resetRegisters();
                h.update(handlers.regMode);

                StepRun.callbbeq = false;
                warning.setVisible(false);
                cross.setVisible(false);
            }
        });


        toolbar.add(cross);
        JLabel junk2 = new JLabel("                ");
        toolbar.add(junk2);
        stop_debug_mode = new JButton();
        stop_debug_mode.setText("Stop Debugging");
        stop_debug_mode.setVisible(false);
        
        stop_debug_mode.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {

                    h.stopdebugmode(evt);
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Debugging Stopped \n",Color.BLACK);
                } catch (IOException ex) {
                }
            }
        });
        toolbar.add(stop_debug_mode);
        onconsole=new JToggleButton();
       onconsole.setFocusable(false);
      onconsole.setBorderPainted(false);
               onconsole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/Terminal.png"))); // NOI18N

        onconsole.setToolTipText("Prints Current Instruction On Console");
       onconsole.doClick();
//        onconsole.addItemListener(new ItemListener() {
//            @Override
//        public void itemStateChanged(ItemEvent ev) {
//        if(ev.getStateChange()==ItemEvent.SELECTED){
//        System.out.println("button is selected");
//      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
//        System.out.println("button is not selected");
//      }
//   }
//});
        JLabel junk3 = new JLabel("                ");
        toolbar.add(junk3);
        toolbar.add(onconsole);
         JLabel junk4 = new JLabel("                ");
        toolbar.add(junk4);
        clr = new JButton();
                        clr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/clear.png"))); // NOI18N

        clr.setFocusable(true);
        clr.setBorderPainted(false);
       // clr.setBorder(null);
       // clr.setText("Clean");
        clr.setToolTipText("Clear the status window");
        
        
        clr.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               // try {
                FrontEnd.statuswindow.setText("");
     
            }
        });
         clr2 = new JButton();
        //clr2.setText("Clear");
                clr2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/resources/clr.jpg"))); // NOI18N

        clr2.setFocusable(false);
        clr2.setBorderPainted(false);
        clr2.setToolTipText("Remove All Highlights");
        
        
        clr2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               // try {
                FrontEnd.activepane.getHighlighter().removeAllHighlights();
     
            }
        });
        toolbar.add(clr);
         JLabel junk5 = new JLabel("                ");
         toolbar.add(junk5);
        toolbar.add(clr2);
        this.add(toolbar, BorderLayout.PAGE_START);

    }

    public static JSplitPane setDividerLocation(final JSplitPane splitter, final double proportion) {
        if (splitter.isShowing()) {
            if (splitter.getWidth() > 0 && splitter.getHeight() > 0) {
                splitter.setDividerLocation(proportion);
            } else {
                splitter.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent ce) {
                        splitter.removeComponentListener(this);
                        setDividerLocation(splitter, proportion);
                    }
                });
            }
        } else {
            splitter.addHierarchyListener(new HierarchyListener() {
                @Override
                public void hierarchyChanged(HierarchyEvent e) {
                    if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0
                            && splitter.isShowing()) {
                        splitter.removeHierarchyListener(this);
                        setDividerLocation(splitter, proportion);
                    }
                }
            });
        }
        return splitter;
    }

//    public static void main(String args[]) {
//        FrontEnd fe = new FrontEnd();
//    }

    private void initializeEditorPane() {

        h.newFile();
        h.update(handlers.regMode);

    }

    private void intializeSidePane() {
        registerPane rp = new registerPane();
        SidePane.add("Register Set", rp);
        memorytable mt = new memorytable();

        SidePane.add("Memory Table", mt);
        JumpTable jt = new JumpTable();

        SidePane.add("Jump Table", jt);
        SidePane.setSelectedIndex(0);

    }

    private void initializecheckpoints() {
    }

    public static void addTab(checkpoint b) {
        if (check.getTabCount() == 1) {
            check.removeTabAt(0);
        }
        check.add("Check Points", b);
    }
 
   

    public static void  highlight(int i ,String find ,Color d)
      { Document document = activepane.getDocument();
//      StyleContext sc = StyleContext.getDefaultStyleContext();
//       AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, d);
//      
                try {
                   
                    for (int index = i; index + find.length() < document.getLength()+1; index++) {
                        //System.out.println("hello");
                        String match = document.getText(index, find.length());
                       // System.out.println("find "+find);
                        //System.out.println("match " +match);
                        if (find.equals(match)) {
                           // System.out.println("match found");
//                            javax.swing.text.DefaultHighlighter.DefaultHighlightPainter highlightPainter =
//                                    new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(d);
                             Highlighter.HighlightPainter highlightPainter =new DefaultHighlighter.DefaultHighlightPainter(
				d);
                            activepane.getHighlighter().addHighlight(index, index + find.length(),
                                   highlightPainter);
                            break;
                             
                        }
                    }
                } 
                catch (BadLocationException ex) {
                    //ex.printStackTrace();
                }
        
      }
      public  static void appendToPane(JTextPane tp, String msg, Color c)
            
    {
        //System.out.println("usahfuashioashoih");
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Times New Roman");
        aset=sc.addAttribute(aset, StyleConstants.Size, 14);
       // aset=sc.addAttribute(aset, StyleConstants.Bold, " ");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        
         try {
      Document doc = tp.getDocument();
      doc.insertString(len, msg, aset);
      
   } catch(BadLocationException exc) {
   }
         frontend.FrontEnd.statuswindow.setCaretPosition(len);
      
       //tp.replaceSelection(msg);
        
    }
}
