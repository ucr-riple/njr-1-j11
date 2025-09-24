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
public class step_out implements Runnable
{

//    public static boolean barrelshiftpresent = false;
    public static Long pos = new Long(0);
    public static RandomAccessFile br = null;
    public static Integer curr_line;
    public static int common;
    public Thread t;
    public ScanFile ob;
    public File fpath;
    public static boolean call;
    public static int initial_pos;
//    public static int end_pos;
    public static int next;
//    private int ld;
    public static int i = 0;
    public static int j = 0;
    private int abc = 0;
    int exitCondition;
    //private static boolean commentflag = false;

    public step_out(File path, int ini, int fin, int f)
      {

        t = new Thread(this, "stepout");
//        t1 = new Thread(this, "step");
        fpath = path;
        exitCondition = ScanFile.call_count;
        if (f == 0 && ini != 0)
        {

            StepRun.ini_line = ini;
            StepRun.fin_line = fin;
            if (ini == fin)
            {
                StepRun.ini_line = 1;
            } else
            {
                StepRun.ini_line = ini;
            }
            //System.out.println("BLAH " + StepRun.ini_line + "BLAH" + StepRun.fin_line + "BLAH" + f);
        }

        f = 1;
      }
//    static String[] shifts = {"ROR", "LSL", "LSR", "RRX", "ASR"};

    
    

    @Override
    public void run()
      {
        try
        {
            //System.out.println("step_out run()");
            scanStart();
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

   

    
    

    public void scanStart() throws InterruptedException
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
                soutLine = soutLine.toUpperCase();
                RemoveComments.setflag(StepRun.commentflag);
                soutLine = RemoveComments.RC(soutLine);
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
                StepRun.commentflag = false;
            } 
            else
            {
                if (frontend.FrontEnd.exceptionraised == 0)
                        {
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, "Successfully Steped Out Of Function \n",Color.GREEN);
                        } else
                        {
                           
                                   
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, "Check Syntax Failed To Step Out   \n",Color.RED);
                        }
              
                ScanFile.pos = (Long) ScanFile.br.getFilePointer();
            }
        } 
        catch (IOException e)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR( " + ScanFile.current_line + "): Unable To Access The Source File " + fpath.getName() + "\n",Color.RED);
//            FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Unable to access the source file: " + fpath.getName() + "\n");
            FrontEnd.statuswindow.setCaretPosition(FrontEnd.statuswindow.getText().length());
            FrontEnd.exceptionraised++;
        }
      }
}
