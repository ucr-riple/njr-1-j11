/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import frontend.FrontEnd;
import frontend.handlers;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Tushar
 */
public class step_over implements Runnable
{

    public static boolean barrelshiftpresent = false;
    public static Long pos = new Long(0);
    public static RandomAccessFile br = null;
    public static Integer current_line;
    public static int ocommon;
    public static int otransfer;
    public static int in_line, fi_line, f_int;
    public Thread t;
    public ScanFile ob;
    public File fpath;
    public static boolean ocall;
    public static int initial_pos;
    public static int end_pos;
    public static int k = 0;
    public static int onext;
    public static int callEncounter;
    public static int exitCondition = 0;
    StepRun obj;
    // private static boolean commentflag = false;

    public step_over(File path, int ini_line, int fin_line, int f)
      {
        exitCondition = ScanFile.call_count;
        t = new Thread(this, "stepover");
        in_line = ini_line;
        fi_line = fin_line;
        f_int = f;

        fpath = path;
      }


    
    

    @Override
    public void run()
      {
        try
        {
            //System.out.println("step_out run()");
            scanstart();
        } catch (InterruptedException ex)
        {
            Logger.getLogger(step_out.class.getName()).log(Level.SEVERE, null, ex);
        }
      }

    public static boolean branchToProcedure(String strLine) throws BadLocationException
      {

        if (new StringTokenizer(strLine, " ,\t").countTokens() == 2 && strLine.toUpperCase().contains("BL"))
        {
            //  frontend.FrontEnd.activepane.getHighlighter().addHighlight(initial_pos, end_pos, frontend.handlers.cyanPainter);
            return true;
        }
        return false;
      }

   

    public void scanstart() throws InterruptedException
      {

        obj = new StepRun(backend.ScanFile.path, in_line, fi_line, f_int);

        obj.t.start();

        if (callEncounter > 0)
        {
//        curr_line = new Integer(0);
            try
            {
                String soutLine = null;
                if (br == null)
                {
//                br = new RandomAccessFile(fpath, "r");
                    soutLine = new String();
                    ScanFile.pos = ScanFile.br.getFilePointer();
                    initial_pos = ScanFile.pos.intValue();
                    Register.r[16].b = ScanFile.pos.intValue();
//                br.seek(initial_pos);
                    ScanFile.current_line = ScanFile.getLineNumber(FrontEnd.activepane, initial_pos);
                }
                while (ScanFile.call_count >= exitCondition && (soutLine = ScanFile.br.readLine()) != null)
                {
                    ScanFile.pos = (Long) ScanFile.br.getFilePointer();

                    (new handlers()).update(handlers.regMode);
                    Register.r[16].b = ScanFile.pos.intValue();
                    initial_pos = ScanFile.pos.intValue();
                    ScanFile.current_line = ScanFile.getLineNumber(FrontEnd.activepane, initial_pos);

                    RemoveComments.setflag(StepRun.commentflag);
                    soutLine = (RemoveComments.RC(soutLine)).toUpperCase();
                    StepRun.commentflag = RemoveComments.getcommetnflag();
                    if (soutLine.contains(":"))
                    {
                        StringTokenizer st3 = new StringTokenizer(soutLine, ":");
                        int ct_label = st3.countTokens();
                        if (ct_label == 1)
                        {
                            continue;
                        } else
                        {
                            if (ct_label == 2)
                            {
                                soutLine = st3.nextToken();
                                soutLine = st3.nextToken();
                            } else
                            {
                                while (st3.hasMoreTokens())
                                {
                                    soutLine += st3.nextToken();
                                }
                            }
                            soutLine = soutLine.trim();
                            if (!soutLine.isEmpty())
                            {
                                ScanFile.tokenizeInstruction(soutLine);
                                if (frontend.FrontEnd.exceptionraised == 0)
                        {
                            FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.GREEN);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,ScanFile.stepins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Executed Successfully \n",Color.GREEN);
                        } else
                        {
                           FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.RED);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,ScanFile.stepins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Failed To Execute \n",Color.RED);
                        }
                            }
                        }
                    } else
                    {
                        if (!soutLine.isEmpty())
                        {
                            ScanFile.tokenizeInstruction(soutLine);
                            if (frontend.FrontEnd.exceptionraised == 0)
                        {
                            FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.GREEN);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,ScanFile.stepins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Executed Successfully \n",Color.GREEN);
                        } else
                        {
                            FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.RED);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,ScanFile.stepins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Failed To Execute \n",Color.RED);
                        }
                        }
                    }

                }
                if (soutLine == null)
                {
                    StepRun.callbbeq = false;
                    FrontEnd.warning.setVisible(true);
                    FrontEnd.cross.setVisible(true);
                    FrontEnd.cross.setEnabled(true);
                    FrontEnd.steprun.setVisible(false);
                    FrontEnd.run_stepOut.setEnabled(false);
                    FrontEnd.run_stepOver.setEnabled(false);
                    FrontEnd.r_stepOut.setEnabled(false);
                    FrontEnd.r_stepover.setEnabled(false);
                    return;
                } else
                {
//                pos = (Long) br.getFilePointer();
//                ScanFile.br.seek(pos);
                    ScanFile.pos = (Long) ScanFile.br.getFilePointer();
                }
            } catch (IOException e)
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Unable To Access The Source File: " + fpath.getName() + "\n",Color.RED);
//                FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Unable to access the source file: " + fpath.getName() + "\n");
                FrontEnd.statuswindow.setCaretPosition(FrontEnd.statuswindow.getText().length());
                FrontEnd.exceptionraised++;
            } finally
            {
                handlers.debugMode = 0;
            }
        }
      }
}
