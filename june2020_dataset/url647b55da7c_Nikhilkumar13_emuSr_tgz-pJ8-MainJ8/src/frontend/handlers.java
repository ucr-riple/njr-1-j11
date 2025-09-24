/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//latest handelrrs
package frontend;

import backend.*;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author Nikhil
 * @author Tushar
 */
public class handlers
{

    private int num = 0;
    public static int countOpened = 0;
    public static int regMode = 0;
    public static Highlighter.HighlightPainter cyanPainter;
    public static Highlighter.HighlightPainter redpainter;
     static int ini_line = 0, fin_line = 0;
      public static int debugMode = 0;

    public handlers()
      {
        handlers.cyanPainter = new DefaultHighlighter.DefaultHighlightPainter(
				Color.cyan);
         redpainter =new DefaultHighlighter.DefaultHighlightPainter(
				Color.red);
      }

    void newFile()
      {

        //FrontEnd.statuswindow.append("Opening Blank File..\n");
        FrontEnd.appendToPane(FrontEnd.statuswindow,"Opening Blank File\n",Color.BLACK);
        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
        try
        {
            FrontEnd.activepane = new JEditorPane();
            JScrollPane scroll = new JScrollPane();
            scroll.setViewportView(FrontEnd.activepane);
            FrontEnd.activepane.setEditorKit(new NumberedEditorKit());
            FrontEnd.activepane.setText(" ");
            FrontEnd.EditorPane.add("Untitled" + num + ".s", scroll);
            FrontEnd.activepane.setHighlighter(new frontend.MyHighlighter());
          //  FrontEnd.activepane.setSelectionColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));//line replaced by nikhil in place of below on saturday 3:45;
           FrontEnd.activepane.setSelectionColor(new Color(0.0f, 0.0f, 1.0f, 0.1f));
//            LinePainter painter = new LinePainter(FrontEnd.activepane);

            num++;
            countOpened++;

            FrontEnd.EditorPane.setTabComponentAt(countOpened - 1, new ButtonTabComponent(FrontEnd.EditorPane));
            FrontEnd.EditorPane.setSelectedIndex(countOpened - 1);
            FrontEnd.filepath = null;
            backend.FirstPass.branchtable = new Hashtable();
            new Memory();
            backend.Register.resetRegisters();
            clean_branchtable();
            clean_memtable();
            //setTitle(filename);
            //FrontEnd.statuswindow.append("New blank file opened..\n");
             FrontEnd.appendToPane(FrontEnd.statuswindow,"New Blank File Opened\n",Color.BLACK);
           // FrontEnd.statuswindow.setCaretPosition(FrontEnd.statuswindow.getText().length());

        } catch (Exception e)
        {
            //e.printStackTrace();
           // FrontEnd.statuswindow.append("Some error occured while opening new file\n");
             FrontEnd.appendToPane(FrontEnd.statuswindow,"Some Error Occured While Opening New File\n",Color.BLACK);
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());

        }
      }

    void openfile()
      {

       // FrontEnd.statuswindow.append("Opening File..\n");
         FrontEnd.appendToPane(FrontEnd.statuswindow,"OpeningFile\n",Color.BLACK);
        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
        backend.Register.resetRegisters();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new MyCustomFilter());
        int returnVal = fileChooser.showOpenDialog(new javax.swing.JFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            try
            {
                // What to do with the file, e.g. display it in a TextArea

                open(file);
                backend.Register.resetRegisters();
                clean_branchtable();
                clean_memtable();
            } catch (Exception ex)
            {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"Problem In Accessing File"+file.getAbsolutePath()+"\n",Color.BLACK);
//                FrontEnd.statuswindow.append( + file.getAbsolutePath() + "\n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());

            }
        } 
        else
        {

//            FrontEnd.statuswindow.append("File access cancelled by user.\n");
            FrontEnd.appendToPane(FrontEnd.statuswindow,"File access canceled by user\n",Color.BLACK);
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());

        }    // TODO add your handling code here:

      }

    void savefile()
      {
//        FrontEnd.statuswindow.append("Saving File...\n");
        FrontEnd.appendToPane(FrontEnd.statuswindow,"Saving File...\n",Color.BLACK);
        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
        int id = FrontEnd.EditorPane.getSelectedIndex();
        if (FrontEnd.EditorPane.getToolTipTextAt(id) == null)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new MyCustomFilter());
            int returnVal = fileChooser.showSaveDialog(new javax.swing.JFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                try
                {

                    FrontEnd.filepath = fileChooser.getSelectedFile().getPath() + ".s";
                    File n = new File(FrontEnd.filepath);
                    BufferedWriter out = new BufferedWriter(new FileWriter(FrontEnd.filepath));
                    out.write(FrontEnd.activepane.getText());
                    String fname = fileChooser.getSelectedFile().getName();
                    JOptionPane.showMessageDialog(null, "Saved " + ((fileChooser.getSelectedFile() != null) ? fname : "nothing"));
                    FrontEnd.EditorPane.setTitleAt(id, fname);
                    FrontEnd.EditorPane.setToolTipTextAt(id, FrontEnd.filepath);
                    out.close();
                    backend.Register.resetRegisters();
                    clean_branchtable();
                    clean_memtable();

                } catch (Exception e)
                {
                    //FrontEnd.statuswindow.append("Error occured while saving file...\n");
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File\n",Color.BLACK);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                   // FrontEnd.statuswindow.append(e.getStackTrace().toString());
                    FrontEnd.appendToPane(FrontEnd.statuswindow,e.getStackTrace().toString(),Color.BLACK);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    JOptionPane.showMessageDialog(null, "Saving File cancelled.");
                    frontend.FrontEnd.exceptionraised++;
                }
            } else
            {
//                FrontEnd.statuswindow.append("Saving cancelled by user.\n");
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Saving Cancelled By User\n",Color.BLACK);
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            }
        } else
        {
            BufferedWriter out = null;
            try
            {
                FrontEnd.filepath = FrontEnd.EditorPane.getToolTipTextAt(id);
                out = new BufferedWriter(new FileWriter(FrontEnd.filepath));
                out.write(FrontEnd.activepane.getText());
            } catch (IOException ex)
            {
               // FrontEnd.statuswindow.append("Error occured while saving file...\n");
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File\n",Color.BLACK);
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
             //   FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            } finally
            {
                try
                {
                    out.close();
                } catch (IOException ex)
                {
                    //FrontEnd.statuswindow.append("Error occured while saving file...\n");
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File/n",Color.BLACK);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    //FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                    FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getMessage(),Color.BLACK);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    //frontend.FrontEnd.exceptionraised++;
                }
            }

        }
      }

    void exit()
      {
        System.exit(0);
      }

    void cut() throws BadLocationException
      {
        String sel = FrontEnd.activepane.getSelectedText();
        frontend.FrontEnd.activepane.getHighlighter().addHighlight(FrontEnd.activepane.getSelectionStart(), FrontEnd.activepane.getSelectionEnd(), frontend.handlers.cyanPainter);
        StringSelection ss = new StringSelection(sel);
        FrontEnd.clip.setContents(ss, ss);

        FrontEnd.activepane.replaceSelection("");
      }

    void copy()
      {
        String sel = FrontEnd.activepane.getSelectedText();
        StringSelection clipString = new StringSelection(sel);
        FrontEnd.clip.setContents(clipString, clipString);
      }

    void paste()
      {
        Transferable cliptran = FrontEnd.clip.getContents(this);
        try
        {
            String sel = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
            FrontEnd.activepane.replaceSelection(sel);
        } catch (Exception ex)
        {
            Logger.getLogger(handlers.class.getName()).log(Level.SEVERE, null, ex);
        }

      }

    void run()
      {
        FrontEnd.exceptionraised = 0;
        new Memory();
        backend.Register.resetRegisters();
        clean_branchtable();
        clean_memtable();
         FrontEnd.activepane.getHighlighter().removeAllHighlights();
       

        backend.Register.r[15].b = backend.ScanFile.endOfProgram;

//        sActionPerformed(evt);
        BufferedWriter out = null;
        File fin = null;

        int id = FrontEnd.EditorPane.getSelectedIndex();
        if (FrontEnd.EditorPane.getToolTipTextAt(id) != null)
        {
            //FrontEnd.statuswindow.append("Saving File...\n");
            FrontEnd.appendToPane(FrontEnd.statuswindow,"Saving File\n",Color.BLACK);
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            try
            {
                FrontEnd.filepath = FrontEnd.EditorPane.getToolTipTextAt(id);
                fin = new File(FrontEnd.filepath);
                out = new BufferedWriter(new FileWriter(fin));
                out.write(FrontEnd.activepane.getText());
            } catch (IOException ex)
            {
                //FrontEnd.statuswindow.append("Error occured while saving file...\n");
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File...\n",Color.BLACK);
//                FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            } finally
            {
                try
                {
                    out.close();
                } catch (IOException ex)
                {
//                    FrontEnd.statuswindow.append("Error occured while saving file...\n");
//                    FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File...\n",Color.BLACK);
//                FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                    
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                }
            }
           // FrontEnd.statuswindow.append("PROGRAM STARTED " + FrontEnd.filepath + "\n");
            FrontEnd.appendToPane(FrontEnd.statuswindow,"PROGRAM STARTED " + FrontEnd.filepath + "\n",Color.BLACK);
            FrontEnd.appendToPane(FrontEnd.statuswindow,"Running..\n",Color.GREEN);

            
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            clean_branchtable();

            clean_memtable();
            Integer ct = new Integer(0);
            ct = backend.FirstPass.scan(fin);
            //System.out.println("ct returned:" + ct);
//            FrontEnd.statuswindow.append("Running.. \n");
            //FrontEnd.appendToPane(FrontEnd.statuswindow,"Runing...\n",Color.BLACK);

            
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            try
            {
                backend.ScanFile ob = new backend.ScanFile(fin);
                update_branchtable(ct);
                update(handlers.regMode);

            } catch (Exception e)
            {
            }
        } else
        {
            try
            {
                // Create temp file.
                File temp = File.createTempFile("temp", ".s");

                FrontEnd.filepath = null;
                out = null;
                try
                {

                    out = new BufferedWriter(new FileWriter(temp));
                    out.write(FrontEnd.activepane.getText());
                } catch (IOException ex)
                {
                    //FrontEnd.statuswindow.append("Error occured ...\n");
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured \n",Color.BLACK);

//                    FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                     FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                } finally
                {
                    try
                    {
                        out.close();
                    } catch (IOException ex)
                    {
//                        FrontEnd.statuswindow.append("Error occured ...\n");
//                        FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                         
                        FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured...\n",Color.BLACK);
                         FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                        //frontend.FrontEnd.exceptionraised++;
                    }
                }
//                FrontEnd.statuswindow.append("PROGRAM STARTED \n"); 
                FrontEnd.appendToPane(FrontEnd.statuswindow,"PROGRAM STARTED\n",Color.BLACK);
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                FrontEnd.appendToPane(FrontEnd.statuswindow,"Running..\n",Color.GREEN);
                clean_branchtable();

                clean_memtable();
                Integer ct = new Integer(0);
                ct = backend.FirstPass.scan(temp);
                //System.out.print("ct returned: " + ct);
//                FrontEnd.statuswindow.append("Run:\n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                try
                {
                    backend.ScanFile ob = new backend.ScanFile(temp);
                    update_branchtable(ct);
                    update(handlers.regMode);

                } catch (Exception e)
                {
                }
                // Delete temp file when program exits.
                temp.deleteOnExit();

                // Write to temp file
                //BufferedWriter out = new BufferedWriter(new FileWriter(temp));
                //out.write("aString");
                //out.close();
            } catch (IOException e)
            {
            }
        }

      }
   

    static void assignIniFin(int a, int b)
      {
        ini_line = a;
        fin_line = b;
        //System.out.println(ini_line + ", " + fin_line + " in assignIniFin");
      }
   

    void stepover()
      {
        frontend.FrontEnd.steprun.setVisible(true);
        FrontEnd.stop_debug_mode.setVisible(true);
        step_over.callEncounter = 0;
        debugMode = 2;
      }

    void stepout()
      {
        frontend.FrontEnd.steprun.setVisible(true);
        FrontEnd.stop_debug_mode.setVisible(true);
        debugMode = 1;

      }

    void stepinto()
      {
        debugMode = 0;
        new Memory();
        checkpoint.reset();
        backend.StepRun.transfer = 0;
        backend.step_over.otransfer = 0;
        backend.step_out.common = 0;
        backend.step_out.call = false;
        ScanFile.call_count = 0;
        backend.step_out.next = 0;
        backend.step_over.ocall = false;
        backend.step_over.ocommon = 0;
        backend.step_out.i = 0;
        backend.step_out.j = 0;
        backend.step_over.k = 0;
        backend.step_over.onext = 0;
        ini_line = 0;
        fin_line = 0;
        backend.StepRun.ini_line = 0;
        backend.StepRun.fin_line = 0;
        backend.StepRun.initial_pos = 0;
        BufferedWriter out = null;
        backend.Register.resetRegisters();
        clean_branchtable();
        clean_memtable();
        FrontEnd.run_stepOut.setEnabled(true);
        FrontEnd.run_stepOver.setEnabled(true);

        FrontEnd.r_stepOut.setEnabled(true);
        FrontEnd.r_stepover.setEnabled(true);
        FrontEnd.steprun.setEnabled(true);
        FrontEnd.run_Next.setEnabled(true);

        FrontEnd.reset.setEnabled(true);
        try
        {
            FrontEnd.exceptionraised = 0;
            File fin = null;
            int id = FrontEnd.EditorPane.getSelectedIndex();
            backend.Register.r[16].b = 0;

            backend.ScanFile.current_line = new Integer(0);
            if (FrontEnd.EditorPane.getToolTipTextAt(id) != null)
            {
//                FrontEnd.statuswindow.append("Saving File...\n");
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"Saving File..\n",Color.BLACK);
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                try
                {
                    FrontEnd.filepath = FrontEnd.EditorPane.getToolTipTextAt(id);
                    fin = new File(FrontEnd.filepath);
                    out = new BufferedWriter(new FileWriter(fin));
                    out.write(FrontEnd.activepane.getText());
                   // FrontEnd.statuswindow.append("PROGRAM STARTED " + FrontEnd.filepath + "\n");
                     FrontEnd.appendToPane(FrontEnd.statuswindow,"PROGRAM STARTED\n",Color.GREEN);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    //StepRunWindow obj = new StepRunWindow(new javax.swing.JFrame(), true);
                    backend.ScanFile.path = fin;
                    backend.ScanFile.br = new RandomAccessFile(fin, "r");
                    int ct_line = 0;
                    backend.ScanFile.br.seek(0);

                    String file_text = frontend.FrontEnd.activepane.getText();
                    String[] lines = file_text.split("\r\n|\r|\n");
                    ct_line = lines.length;
                    //System.out.println("number of lines " + ct_line);

                    backend.ScanFile.pos = backend.ScanFile.br.getFilePointer();
                    //obj.call(fin);
                    FrontEnd.steprun.setVisible(true);
                    FrontEnd.stop_debug_mode.setVisible(true);

                    f = 0;
                    b = new checkpoint(ct_line);
                    FrontEnd.addTab(b);

                } catch (IOException ex)
                {
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File\n",Color.BLACK);
//                    FrontEnd.statuswindow.append("Error occured while saving file...\n");
                   // FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                     FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                } finally
                {
                    try
                    {
                        out.close();
                    } catch (IOException ex)
                    {
                        //FrontEnd.statuswindow.append("Error occured while saving file...\n");
                        //FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                         FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured While Saving File\n",Color.BLACK);
                          FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
                        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                        frontend.FrontEnd.exceptionraised++;
                    }
                }

            } else
            {
                try
                {
                    // Create temp file.
                    File temp = File.createTempFile("temp", ".s");
                    FrontEnd.filepath = null;
                    out = null;
                    try
                    {
                        out = new BufferedWriter(new FileWriter(temp));
                        out.write(FrontEnd.activepane.getText());
                        //statuswindow.append("Compiling source file " + filepath + "\n");
                        // StepRunWindow obj = new StepRunWindow(new javax.swing.JFrame(), true);
                        backend.ScanFile.path = temp;
                        // obj.call(temp);
                        backend.ScanFile.br = new RandomAccessFile(temp, "rw");
                        backend.ScanFile.pos = backend.ScanFile.br.getFilePointer();
                        FrontEnd.steprun.setVisible(true);
                        FrontEnd.stop_debug_mode.setVisible(true);
                        int ct_line = 0;
                        backend.ScanFile.br.seek(0);

                        String file_text = frontend.FrontEnd.activepane.getText();
                        String[] lines = file_text.split("\r\n|\r|\n");
                        ct_line = lines.length;
                        //System.out.println("number of lines " + ct_line);

                        backend.ScanFile.pos = backend.ScanFile.br.getFilePointer();
                        //obj.call(fin);
                        FrontEnd.steprun.setVisible(true);
                        FrontEnd.stop_debug_mode.setVisible(true);

                        f = 0;
                        b = new checkpoint(ct_line);
                        FrontEnd.addTab(b);

                    } catch (IOException ex)
                    {
                         FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured..\n",Color.BLACK);
                         FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
//                         FrontEnd.statuswindow.append("Error occured ...\n");
//                        FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    } finally
                    {
                        try
                        {
                            out.close();
                        } catch (IOException ex)
                        {
                             FrontEnd.appendToPane(FrontEnd.statuswindow,"Error Occured...\n",Color.BLACK);
                            FrontEnd.appendToPane(FrontEnd.statuswindow,ex.getStackTrace().toString(),Color.BLACK);
//                             FrontEnd.statuswindow.append("Error occured ...\n");
//                            FrontEnd.statuswindow.append(ex.getStackTrace().toString());
                            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                            //frontend.FrontEnd.exceptionraised++;
                        }
                    }

                    temp.deleteOnExit();

                } catch (IOException e)
                {
                }
            }

        } catch (Exception ex)
        {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }

      }

    public static void update_memorytable(int memref_ct, int ch)
      {
        Object[][] obj = backend.Memory.display(ch);

        clean_memtable();
        for (int i = 0; i < memref_ct; i++)
        {
            memorytable.model2.addRow(obj[i]);
            //System.err.println("updating mem table");

        }
      }

    public static void clean_branchtable()
      {
        int c = JumpTable.model2.getRowCount();
        for (int i = c - 1; i >= 0; i--)
        {
            JumpTable.model2.removeRow(i);

        }
      }

    public static void clean_memtable()
      {
        int c = memorytable.model2.getRowCount();
        for (int i = c - 1; i >= 0; i--)
        {
            memorytable.model2.removeRow(i);

        }
      }

    public void open(File file)
      {
        int ob;
        if ((ob = check_if_open(file.getAbsolutePath())) != -1)
        {
            FrontEnd.EditorPane.setSelectedIndex(ob);
        } else
        {
            backend.Register.resetRegisters();
            try
            {
                FrontEnd.activepane = new JEditorPane();
                JScrollPane scroll = new JScrollPane();
//                   scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                scroll.setViewportView(FrontEnd.activepane);
                FrontEnd.activepane.setEditorKit(new NumberedEditorKit());
                FrontEnd.activepane.setHighlighter(new frontend.MyHighlighter());
               // FrontEnd.activepane.setSelectionColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));//line replaced by nikhil in place of below on saturday 3:45;
               FrontEnd.activepane.setSelectionColor(new Color(0.0f, 0.0f, 1.0f, 0.1f));

                FrontEnd.activepane.read(new FileReader(file.getAbsoluteFile()), null);
                FrontEnd.EditorPane.add(file.getName(), scroll);
                countOpened++;
                FrontEnd.EditorPane.setTabComponentAt(countOpened - 1, new ButtonTabComponent(FrontEnd.EditorPane));
                FrontEnd.EditorPane.setSelectedIndex(countOpened - 1);
                FrontEnd.EditorPane.setToolTipTextAt(countOpened - 1, file.getAbsolutePath());

                FrontEnd.filepath = file.getAbsolutePath();
                //System.out.println("I am set wid " + FrontEnd.filepath);

                backend.FirstPass.branchtable = null;
                backend.FirstPass.branchtable = new Hashtable();
                new Memory();
                clean_branchtable();
                clean_memtable();
            } catch (Exception ex)
            {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"Problem Accesing File"+file.getAbsolutePath()+"\n",Color.BLACK);
//                FrontEnd.statuswindow.append("problem accessing file" + file.getAbsolutePath() + "\n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            }
        }
      }

    private int check_if_open(String path)
      {
        for (int i = 0; i < countOpened; i++)
        {
            //System.out.println(path + " checked with " + FrontEnd.EditorPane.getToolTipTextAt(i) + countOpened + "\n");
            if (FrontEnd.EditorPane.getToolTipTextAt(i) != null)
            {
                if (FrontEnd.EditorPane.getToolTipTextAt(i).equals(path))
                {
                    return i;
                }
            }
        }
        return -1;

      }

    public static void update_branchtable(Integer n)
      {
        Object[][] obj = backend.FirstPass.obj;
        //System.out.print("in updatebranchtable" + n + ": " + obj);
        clean_branchtable();
        for (int i = 0; i < n; i++)
        {
            JumpTable.model2.addRow(obj[i]);
            //System.out.print(obj);
        }
      }

    public void update(final int ch)
      {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
              {

                registerPane.GT_t.setText(Integer.toString(backend.NFlagRegister.GT));
                registerPane.E_t.setText(Integer.toString(backend.NFlagRegister.E));
                try
                {
                    registerPane.PC_t.setText(Integer.toString(backend.ScanFile.current_line + 1));
                } catch (Exception e)
                {
                    registerPane.PC_t.setText(Integer.toString(1));

                }
                // registerPane.v_t.setText(Integer.toString(backend.Condition.cpsr.V));
                if (ch == 0)
                {
                    registerPane.r0_t.setText(String.valueOf(backend.Register.r[0].b));
                    registerPane.r1_t.setText(String.valueOf(backend.Register.r[1].b));
                    registerPane.r2_t.setText(String.valueOf(backend.Register.r[2].b));
                    registerPane.r3_t.setText(String.valueOf(backend.Register.r[3].b));
                    registerPane.r4_t.setText(String.valueOf(backend.Register.r[4].b));
                    registerPane.r5_t.setText(String.valueOf(backend.Register.r[5].b));
                    registerPane.r6_t.setText(String.valueOf(backend.Register.r[6].b));
                    registerPane.r7_t.setText(String.valueOf(backend.Register.r[7].b));
                    registerPane.r8_t.setText(String.valueOf(backend.Register.r[8].b));
                    registerPane.r9_t.setText(String.valueOf(backend.Register.r[9].b));
                    registerPane.r10_t.setText(String.valueOf(backend.Register.r[10].b));
                    registerPane.r11_t.setText(String.valueOf(backend.Register.r[11].b));
                    registerPane.r12_t.setText(String.valueOf(backend.Register.r[12].b));
                    registerPane.r13_t.setText(String.valueOf(backend.Register.r[13].b));
                    registerPane.r14_t.setText(String.valueOf(backend.Register.r[14].b));
                    registerPane.r15_t.setText(String.valueOf(backend.Register.r[15].b));

                } else if (ch == 1)
                {
                    registerPane.r0_t.setText(Integer.toBinaryString(backend.Register.r[0].b));
                    registerPane.r1_t.setText(Integer.toBinaryString(backend.Register.r[1].b));
                    registerPane.r2_t.setText(Integer.toBinaryString(backend.Register.r[2].b));
                    registerPane.r3_t.setText(Integer.toBinaryString(backend.Register.r[3].b));
                    registerPane.r4_t.setText(Integer.toBinaryString(backend.Register.r[4].b));
                    registerPane.r5_t.setText(Integer.toBinaryString(backend.Register.r[5].b));
                    registerPane.r6_t.setText(Integer.toBinaryString(backend.Register.r[6].b));
                    registerPane.r7_t.setText(Integer.toBinaryString(backend.Register.r[7].b));
                    registerPane.r8_t.setText(Integer.toBinaryString(backend.Register.r[8].b));
                    registerPane.r9_t.setText(Integer.toBinaryString(backend.Register.r[9].b));
                    registerPane.r10_t.setText(Integer.toBinaryString(backend.Register.r[10].b));
                    registerPane.r11_t.setText(Integer.toBinaryString(backend.Register.r[11].b));
                    registerPane.r12_t.setText(Integer.toBinaryString(backend.Register.r[12].b));
                    registerPane.r13_t.setText(Integer.toBinaryString(backend.Register.r[13].b));
                    registerPane.r14_t.setText(Integer.toBinaryString(backend.Register.r[14].b));
                    registerPane.r15_t.setText(Integer.toBinaryString(backend.Register.r[15].b));

                } else if (ch == 2)
                {
                    registerPane.r0_t.setText(Integer.toHexString(backend.Register.r[0].b));
                    registerPane.r1_t.setText(Integer.toHexString(backend.Register.r[1].b));
                    registerPane.r2_t.setText(Integer.toHexString(backend.Register.r[2].b));
                    registerPane.r3_t.setText(Integer.toHexString(backend.Register.r[3].b));
                    registerPane.r4_t.setText(Integer.toHexString(backend.Register.r[4].b));
                    registerPane.r5_t.setText(Integer.toHexString(backend.Register.r[5].b));
                    registerPane.r6_t.setText(Integer.toHexString(backend.Register.r[6].b));
                    registerPane.r7_t.setText(Integer.toHexString(backend.Register.r[7].b));
                    registerPane.r8_t.setText(Integer.toHexString(backend.Register.r[8].b));
                    registerPane.r9_t.setText(Integer.toHexString(backend.Register.r[9].b));
                    registerPane.r10_t.setText(Integer.toHexString(backend.Register.r[10].b));
                    registerPane.r11_t.setText(Integer.toHexString(backend.Register.r[11].b));
                    registerPane.r12_t.setText(Integer.toHexString(backend.Register.r[12].b));
                    registerPane.r13_t.setText(Integer.toHexString(backend.Register.r[13].b));
                    registerPane.r14_t.setText(Integer.toHexString(backend.Register.r[14].b));
                    registerPane.r15_t.setText(Integer.toHexString(backend.Register.r[16].b));

                } else if (ch == 3)
                {
                    registerPane.r0_t.setText(Integer.toOctalString(backend.Register.r[0].b));
                    registerPane.r1_t.setText(Integer.toOctalString(backend.Register.r[1].b));
                    registerPane.r2_t.setText(Integer.toOctalString(backend.Register.r[2].b));
                    registerPane.r3_t.setText(Integer.toOctalString(backend.Register.r[3].b));
                    registerPane.r4_t.setText(Integer.toOctalString(backend.Register.r[4].b));
                    registerPane.r5_t.setText(Integer.toOctalString(backend.Register.r[5].b));
                    registerPane.r6_t.setText(Integer.toOctalString(backend.Register.r[6].b));
                    registerPane.r7_t.setText(Integer.toOctalString(backend.Register.r[7].b));
                    registerPane.r8_t.setText(Integer.toOctalString(backend.Register.r[8].b));
                    registerPane.r9_t.setText(Integer.toOctalString(backend.Register.r[9].b));
                    registerPane.r10_t.setText(Integer.toOctalString(backend.Register.r[10].b));
                    registerPane.r11_t.setText(Integer.toOctalString(backend.Register.r[11].b));
                    registerPane.r12_t.setText(Integer.toOctalString(backend.Register.r[12].b));
                    registerPane.r13_t.setText(Integer.toOctalString(backend.Register.r[14].b));
                    registerPane.r14_t.setText(Integer.toOctalString(backend.Register.r[15].b));
                    registerPane.r15_t.setText(Integer.toOctalString(backend.Register.r[16].b));
                }
              }
        });
      }

    void EditorStateChanged(ChangeEvent evt)
      {

        JScrollPane sp = (JScrollPane) FrontEnd.EditorPane.getSelectedComponent();
        if (FrontEnd.EditorPane.getTabCount() != 0)
        {
            //System.out.print("no of components :" + FrontEnd.EditorPane.getComponentCount());
            JViewport vp = (JViewport) sp.getViewport();
            //JEditorPane ep = new JEditorPane();
            //if(! (vp.getComponent(0) instanceof frontend.setbkpt))
            
            {
                FrontEnd.activepane = (JEditorPane) vp.getComponent(0);
                if (FrontEnd.EditorPane.getToolTipText() != null)
                {
                    FrontEnd.filepath = FrontEnd.EditorPane.getToolTipTextAt(FrontEnd.EditorPane.getSelectedIndex());
                } else
                {
                    FrontEnd.filepath = new String();
                }
                //System.out.println("Editor state changed to " + FrontEnd.filepath);
            }
        }
        else
        {
        }

      }
    checkpoint b;
    static StepRun obj;
    static step_out obj2;
    static step_over obj3;
    static int f = 0;

    public void steprun2() throws IOException
      {
//        FrontEnd.exceptionraised = 0;
//        handlers.clean_branchtable();
//        handlers.clean_memtable();
        FrontEnd.activepane.getHighlighter().removeAllHighlights();
         //FrontEnd.activepane.setSelectionColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));
       
        Integer ct = new Integer(0);
        //System.out.println("ini_line ->" + ini_line + "fin_liine->" + fin_line + "f ->" + f);

        ct = backend.FirstPass.scan(backend.ScanFile.path);
        handlers.update_branchtable(ct);
        if (f == 0 && ini_line != fin_line)
        {
            //System.out.println("\n 0101010101010101");
            for (int i = 1; i < ini_line; i++)
            {
                ScanFile.br.readLine();
            }
        }

        if (debugMode == 0)
        {
            //System.out.println("1234 " + ini_line + "asdf" + fin_line);
            obj = new StepRun(backend.ScanFile.path, ini_line, fin_line, f);
            //System.out.println("steprun2()debugmode=0");
            f = 1;

            obj.t.start();
        } else if (debugMode == 1)
        {
            obj2 = new step_out(backend.ScanFile.path, ini_line, fin_line, f);
            obj2.t.start();
            //System.out.println("steprun2()debugmode=1");
            f = 1;
            debugMode = 0;
        } else if (debugMode == 2)
        {
            obj3 = new step_over(backend.ScanFile.path, ini_line, fin_line, f);
            obj3.t.start();
            //System.out.println("steprun2()debugmode=2");
        }
        (new handlers()).update(handlers.regMode);
        int memref_ct = Memory.getsize();
        if (memref_ct > 0)
        {
            handlers.update_memorytable(memref_ct, handlers.regMode);
        }
//        if (FrontEnd.exceptionraised != 0)
//        {
//             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR IN EXECUTION  ",Color.BLACK);
//              FrontEnd.appendToPane(FrontEnd.statuswindow,FrontEnd.exceptionraised +" \n",Color.BLACK);
////            FrontEnd.statuswindow.append("ERROR IN EXECUTION " + FrontEnd.exceptionraised + "\n");
//            FrontEnd.exceptionraised = 0;
//        }

        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());// TODO add your handling code here:

      }

    void steprunActionPerformed(ActionEvent evt) throws IOException
      {
        steprun2();
        // //System.out.println("\n stepingggggggg");
      }

    void stopdebugmode(ActionEvent evt) throws IOException
      {
        //System.out.println("\n Step run stopeed ");
        FrontEnd.activepane.getHighlighter().removeAllHighlights();
        StepRun.callbbeq = false;
        backend.StepRun.commentflag=false;
        clean_memtable();
        clean_branchtable();
        
        
        
        Register.resetRegisters();
        update(regMode);
        frontend.FrontEnd.steprun.setEnabled(false);
        FrontEnd.steprun.setVisible(false);
        FrontEnd.stop_debug_mode.setVisible(false);
        FrontEnd.warning.setVisible(false);
        FrontEnd.cross.setEnabled(false);
        FrontEnd.cross.setVisible(false);

        frontend.FrontEnd.run_stepOut.setEnabled(false);
        frontend.FrontEnd.run_stepOver.setEnabled(false);
        frontend.FrontEnd.r_stepOut.setEnabled(false);
        frontend.FrontEnd.r_stepover.setEnabled(false);
        frontend.checkpoint.reset();
        FrontEnd.reset.setEnabled(false);
        frontend.FrontEnd.check.removeTabAt(0);
        

      }

    class MyCustomFilter extends javax.swing.filechooser.FileFilter
    {

        @Override
        public boolean accept(File file)
          {
            // Allow only directories, or files with ".s" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".s");
          }

        @Override
        public String getDescription()
          {
            return "(*.s)";
          }
    }
}
