/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import backend.instructions.InsInterface3;
import frontend.FrontEnd;

import frontend.handlers;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author nikhil
 */
public class ScanFile  implements Runnable 
{

   // public static ihandling ob;
   public static boolean onconsole = false;
    private static boolean commentflag = false;
    public static boolean mainPresent;
    public static int endOfProgram = -1;
    public static long mainPosition;
    Thread t;
    public static File path;
    SetBreakpoint ob_bkpt;
//    public static boolean barrelshiftpresent = false;
    public static int shiftType;
    public static int reg1orimm0;
    public static int regnoOrImmOp;
    public static int shiftregno;
    public static int ini_pos = 0;
    public static Long pos = new Long(0);
    public static RandomAccessFile br = null;
    public static Integer current_line; //which line number is being executed;
    static int ioflag = 0;
    public static int call_count = 0;
    public static String ins="";
    public static String stepins="";
    public static int exr=0;
    public static String rawins="";

    public static int getLineNumber(JEditorPane component, int pos)
      {
        int posLine;
        int y = 0;

        try
        {
            Rectangle caretCoords = component.modelToView(pos);
            y = (int) caretCoords.getY();
        } catch (BadLocationException ex)
        {
        }

        int lineHeight = component.getFontMetrics(component.getFont()).getHeight();
        posLine = (y / lineHeight) + 1;
        return posLine;
      }

    
    

    public ScanFile(File p)
      {

        t = new Thread(this, "scan");
        path = p;
        t.start();
      }

    public ScanFile(File filepath, SetBreakpoint ob_bkpt)
      {
        t = new Thread(this, "scan");
        path = filepath;
        this.ob_bkpt = ob_bkpt;
        t.start();
      }

    public void scanStart()
      {
        

        current_line = new Integer(0);
//System.out.print("hi nikhil " + current_line + "/n");
        backend.Register.r[16].b = 0; //program counter is being initialised to zero
        try
        {
            String strLine = null;
            if (br == null)
            { ///this is to put pointer to main
                File f = path;
                //System.out.println("\n heyyyyyyyyyyyyyyyyyyy");
                br = new RandomAccessFile(f, "r");
                
                if (mainPresent)
                {

                    backend.ScanFile.br.seek(mainPosition);
                    backend.ScanFile.pos = mainPosition;
                    ini_pos = pos.intValue();
                } else
                {
                    ini_pos = 0;
                    pos = new Long(0);
                }

            }
           
            while ((strLine = br.readLine()) != null)
            {
                ini_pos = pos.intValue();
               
                pos = (Long) br.getFilePointer();
               
                

                (new handlers()).update(handlers.regMode);


                backend.Register.r[16].b = pos.intValue();//update the program counter
               
                current_line = getLineNumber(frontend.FrontEnd.activepane, ini_pos);//current line 
                
                rawins=strLine;
                strLine = strLine.trim();

                RemoveComments.setflag(commentflag);
                strLine = backend.RemoveComments.RC(strLine);
                commentflag = RemoveComments.getcommetnflag();
                if (strLine.isEmpty())
                {
                    continue;
                } 
                 
                
                
                    strLine = strLine.toUpperCase();
                
                
                if (strLine.contains(":"))
                {
                    //instruction along with a label
                    StringTokenizer st2 = new StringTokenizer(strLine, ":");
                    int ct_label = st2.countTokens();
                    if (ct_label == 1)
                    {
                        continue;
                    } else
                    {
                        if (ct_label == 2)
                        {
                            strLine = st2.nextToken();
                            strLine = st2.nextToken();

                        } else
                        {
                            for (int i = 0; i < ct_label; i++)
                            {
                                strLine = st2.nextToken();
                            }
                        }
                        //System.out.println("\n label and instruction " + strLine);
                        strLine = strLine.trim();
                        //System.out.println("\n strLine =" + strLine);
                        if (!strLine.isEmpty())
                        {
                            tokenizeInstruction(strLine);
                        }

                    }

                } 
                else
                {
                    tokenizeInstruction(strLine);
                }
               // System.out.println("returned here");
               // System.out.println("exr "+exr);
               // System.out.println("exception "+frontend.FrontEnd.exceptionraised);
                if(exr<frontend.FrontEnd.exceptionraised)
                {
                     //System.out.println("redding");
                   // System.out.println(rawins);
                   // System.out.println(ini_pos);
                    frontend.FrontEnd.highlight(ini_pos,rawins, Color.red);
                   // frontend.FrontEnd.activepane.removeAll();
                    
                }
                
                  exr =frontend.FrontEnd.exceptionraised;
            }
           
                
                  exr =frontend.FrontEnd.exceptionraised;
        } catch (IOException e)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Unable To Access The Source File  " + path.getName() + " \n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Unable to access the source file: " + path.getName() + " \n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
//            return;
        }
      }

    static void tokenizeInstruction(String strLine)
      {
       StringTokenizer tt= new StringTokenizer(strLine,", \t");
       stepins=tt.nextToken();

        strLine = strLine.replace(",", " ");
    
         if(frontend.FrontEnd.onconsole.isSelected())
        {
            System.out.println("Current Instruction -> "+strLine);
        }

        StringTokenizer st = null;
        if (!strLine.contains("["))
        {

            st = new StringTokenizer(strLine, ", \t");

            backend.Emit.execute(st);

        } 
        else if (strLine.contains("["))
        {

            st = new StringTokenizer(strLine, " ");
              ins = st.nextToken();
              Emit.ins=ins;
             String rd="";
             String addressing_mode="";

             if (backend.Emit.ht.get(ins) instanceof InsInterface3)
            {
                InsInterface3 obj1 = (InsInterface3) backend.Emit.ht.get(ins);
                 try
           {
             rd = st.nextToken();
           }
                 catch(Exception e)
                 {
                      FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Destination Register Mising For Instruction "+ins+"\n",Color.RED);

                frontend.FrontEnd.exceptionraised++;
                     
                 }
                 try
                 {
                     
            addressing_mode = st.nextToken();
            while (st.hasMoreTokens())
            {
                addressing_mode += st.nextToken();
            }
           }
                 
           catch(Exception e)
           {    FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Source Register Mising For Instruction "+ins+"\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("\n ERROR in line " + ScanFile.current_line + ": Unknown operation"+"\n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
               
               
           }
                  obj1.execute(ins, rd, addressing_mode);
            }
             else
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Invalid Instruction  "+ins+"\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("\n ERROR in line " + ScanFile.current_line + ": Unknown operation"+"\n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
                return;
            }

          
           
           
        } 
//        else
//        {
//            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Unknown Instruction"+"\n",Color.RED);
////            frontend.FrontEnd.statuswindow.append("\n ERROR in line " + ScanFile.current_line + ": Unknown operation ");
//            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
//            frontend.FrontEnd.exceptionraised++;
////            return;
//        }

      }

    @Override
    public void run()
      {
        try{
        scanStart();
        }
        catch(Exception e)
        {
            
        }

        //System.out.println("\n RETURNED HERE");
        br = null;
        mainPresent = false;
        backend.StepRun.callbbeq = false;
        RemoveComments.flagcomment = false;
        RemoveComments.flag = false;

        // backend.StepRun.stepruncomment=false;
        if (frontend.FrontEnd.exceptionraised == 0)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"PROGRAM COMPLETED SUCCESFULLY\n",Color.GREEN);
//            frontend.FrontEnd.statuswindow.append("PROGRAM COMPLETED SUCCESFULLY\n");
            System.out.println("Program Completed Succesfully \n");
        } 
        else
            
        {   
            //System.out.println("sandklasmdkl");
            FrontEnd.appendToPane(FrontEnd.statuswindow,"PROGRAM FAILED\n",Color.BLACK);
            System.out.println("Program Failed \n");
            
//            frontend.FrontEnd.statuswindow.append("PROGRAM FAILED\n");
            FrontEnd.appendToPane(FrontEnd.statuswindow,"Total Error`:  " + frontend.FrontEnd.exceptionraised+"\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("Total Error`:  " + frontend.FrontEnd.exceptionraised+"\n");

        }
        frontend.FrontEnd.exceptionraised=0;
        exr=0;
        
        //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
      }
}
