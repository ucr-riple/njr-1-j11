package backend;

import frontend.FrontEnd;
//import frontend.LinePainter;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.text.BadLocationException;

public class StepRun implements Runnable
{

    public Thread t;
    public File fpath;
    public static int ini_line = 0;
    public static int fin_line = 0;
    public static int initial_pos;
    public static boolean callbbeq = false;
    public static boolean stepruncomment = false;
    public static int end_pos;
    public static int transfer;
    public static int srun = 0;
    public static boolean commentflag = false;

    public StepRun(File path, int ini, int fin, int f)
      {



        t = new Thread(this, "steprun");
        fpath = path;

        if (f == 0 && ini != 0)
        {
            ini_line = ini;
            fin_line = fin;
            if (ini == fin)
            {
                ini_line = 1;
            } else
            {
                ini_line = ini;
            }
            //System.out.println("yepii " + ini_line + "BLAH" + fin_line + "BLAH" + f);
        }

        f = 1;
        //System.out.println(" \n" + ini + "" + fin + "" + ini_line + " " + fin_line + " in steprun");

      }

    public void scanStart()
      {
        // linePainter = new LinePainter(frontend.FrontEnd.activepane);

        FrontEnd.exceptionraised = 0;
        try{
       // System.err.println("step run scanstart()");
        if (ini_line != 0)
        {
           // System.out.println("STEP RUN UPAR");
           

                ScanFile.pos = ScanFile.br.getFilePointer();
                initial_pos = ScanFile.pos.intValue();

                //System.out.print("val returned is :" + initial_pos + " and length is :" + ScanFile.path.length());
                if (ini_line < fin_line)
                {
                    ScanFile.br.seek(ScanFile.pos);
                } else
                {
                    //System.out.println(" \n stopping step run ");
                    callbbeq = false;
                    commentflag = false;
                    frontend.FrontEnd.steprun.setEnabled(false);
                    frontend.FrontEnd.warning.setVisible(true);
                    frontend.FrontEnd.cross.setVisible(true);
                    frontend.FrontEnd.cross.setEnabled(true);
                    frontend.FrontEnd.run_stepOut.setEnabled(false);
                    frontend.FrontEnd.run_stepOver.setEnabled(false);
                    frontend.FrontEnd.r_stepOut.setEnabled(false);
                    frontend.FrontEnd.r_stepover.setEnabled(false);
                    frontend.FrontEnd.check.removeTabAt(0);

                    FrontEnd.reset.setEnabled(false);
                    return;
                }
                String strLine = "";
                if (!callbbeq)
                {
                    if (initial_pos == 0 & ScanFile.mainPresent)
                    {
                        backend.ScanFile.br.seek(ScanFile.mainPosition);
                        backend.ScanFile.pos = ScanFile.mainPosition;
                        initial_pos = ScanFile.pos.intValue();
                    } else
                    {
                       // initial_pos++;
                    }
                }
                ScanFile.current_line = ScanFile.getLineNumber(frontend.FrontEnd.activepane, initial_pos);
                if ((strLine = ScanFile.br.readLine()) != null && ini_line <= fin_line - 1)
                {
                    ini_line++;
                    //System.out.println("asdfghj " + ini_line + fin_line);
                    //System.out.println("\n" + strLine + " at line" + String.valueOf(backend.ScanFile.current_line));
                    ScanFile.pos = ScanFile.br.getFilePointer();
                     //end_pos=initial_pos+strLine.length();
                    end_pos = ScanFile.pos.intValue();
                    //System.out.println("end pos " + end_pos + "initialpos " + initial_pos);
                    frontend.FrontEnd.activepane.getHighlighter().addHighlight(initial_pos, end_pos, frontend.handlers.cyanPainter);
                    backend.Register.r[16].b = ScanFile.pos.intValue();//update the prgram counter
                    ScanFile.current_line = ScanFile.getLineNumber(frontend.FrontEnd.activepane, initial_pos);
                    //System.out.println("\n" + strLine + " at line" + String.valueOf(backend.ScanFile.current_line));
                    // System.out.println("commentflag in step run " + commentflag);

                    RemoveComments.setflag(commentflag);
                    strLine = backend.RemoveComments.RC(strLine.trim());
                    commentflag = RemoveComments.getcommetnflag();

                    if (strLine.isEmpty())
                    {
                        return;

                    } 
                      frontend.FrontEnd.highlight(initial_pos,strLine, Color.CYAN);
                    strLine=strLine.toUpperCase();
                   if (strLine.contains(":"))
                    {

                        StringTokenizer st2 = new StringTokenizer(strLine, ":");
                        int ct_label = st2.countTokens();
                        if (ct_label == 1)
                        {
                            //System.err.println("label in step run");

                            return;
                        } else
                        {
                            if (ct_label == 2)
                            {
                                strLine = st2.nextToken();
                                strLine = st2.nextToken();

                            }
                            strLine = strLine.trim();

                            ScanFile.tokenizeInstruction(strLine);
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
                    else
                    {
                        ScanFile.tokenizeInstruction(strLine);

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
                {



                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                }
             
           

        } 
        else
        {
            //System.out.println("STEP RUN NEECHE");

            //System.out.println("\n callbbeq" + callbbeq);
            


                initial_pos = ScanFile.pos.intValue();
                //System.out.print("val returnd is :" + initial_pos + " and length is :" + ScanFile.path.length());
                if (initial_pos < (ScanFile.path.length()))
                {
                   // System.out.println("furrrrrrrrrrrrrrrr");
                    ScanFile.br.seek(ScanFile.pos);
                    //System.out.println("initial pos "+initial_pos);
                    //System.out.println("path length "+ScanFile.path.length() );

                } else
                {
                    //System.out.println("\n Step run stopeed ");
                    callbbeq = false;
                    commentflag = false;
                    frontend.FrontEnd.steprun.setEnabled(false);
                    frontend.FrontEnd.warning.setVisible(true);
                    frontend.FrontEnd.cross.setVisible(true);
                    frontend.FrontEnd.run_stepOut.setEnabled(false);
                    frontend.FrontEnd.run_stepOver.setEnabled(false);
                    frontend.FrontEnd.r_stepOut.setEnabled(false);
                    frontend.FrontEnd.r_stepover.setEnabled(false);
                    frontend.checkpoint.reset();
                    FrontEnd.reset.setEnabled(false);
                    return;
                }
                String strLine = new String();
                if (!callbbeq)
                {
                    if (ScanFile.mainPresent & initial_pos == 0)
                    {
                        backend.ScanFile.br.seek(ScanFile.mainPosition);
                        backend.ScanFile.pos = ScanFile.mainPosition;
                        initial_pos = ScanFile.pos.intValue();
                        
                       //initial_pos--;
                       // System.out.println("\n hiiiiiiiiiiii");
                    } 
                    //initial_pos--;
                    
                   
                }
               // if(initial_pos!=0)
                   // initial_pos++;
                
                //System.out.println("\n initial_pos after ++" + initial_pos++);
                if ((strLine = ScanFile.br.readLine()) != null)
                {
//                    frontend.StepRunWindow.jTextField1.setText(String.valueOf(backend.ScanFile.current_line+1));
                    //System.out.println("\n" + strLine + " at line" + String.valueOf(backend.ScanFile.current_line));
//                    ScanFile.barrelshiftpresent = false;
//                    ini_line++;
                    strLine=strLine.trim();
                    ScanFile.pos = ScanFile.br.getFilePointer();
                    end_pos = ScanFile.pos.intValue();
                   // end_pos=initial_pos+strLine.length();
                   // System.out.println(strLine);
                   // System.out.println("askdhkashdaslhdldsl");
                  //  end_pos = ScanFile.pos.intValue();
                   // System.out.println("initial_pos " + initial_pos);
                  // System.out.println(" end_pos " + end_pos );
                   
                   
                   if(!strLine.isEmpty())
                    {
                        //frontend.FrontEnd.highlight(initial_pos-5,strLine, Color.CYAN);
                       
                        
                     // frontend.FrontEnd.activepane.getHighlighter().addHighlight(initial_pos, end_pos, frontend.handlers.cyanPainter);
                    }
                  
                    //frontend.FrontEnd.activepane.getHighlighter().
                    //  linePainter.setColor(Color.yellow);
                    // linePainter.resetHighlight(15);
                    backend.Register.r[16].b = ScanFile.pos.intValue();//update the prgram counter
                    ScanFile.current_line = ScanFile.getLineNumber(frontend.FrontEnd.activepane, initial_pos-1);
                    //System.out.println(ScanFile.current_line);
                    strLine = strLine.trim();
                    // System.out.println("commentflag in step run " + commentflag);

                    RemoveComments.setflag(commentflag);
                    strLine = backend.RemoveComments.RC(strLine);
                    commentflag = RemoveComments.getcommetnflag();
                    //System.out.println("\n Value step run comment on " + commentflag);
                    if (strLine.isEmpty())
                    {
                        //System.out.println("empty in step run");
                        return;
                    }
                   // System.out.println("paintinggggggggggggggg");
                    //System.out.println(strLine);
                    frontend.FrontEnd.highlight(initial_pos,strLine, Color.CYAN);
                    strLine=strLine.toUpperCase();
                    //System.out.println(strLine);
                     //
                    //

                    if (strLine.contains(":"))
                    {

                        StringTokenizer st2 = new StringTokenizer(strLine, ":");
                        int ct_label = st2.countTokens();
                        if (ct_label == 1)
                        {
                            //System.out.println("\n label while steprun");
                            return;
                        } else
                        {
                            if (ct_label == 2)
                            {
                                strLine = st2.nextToken();
                                strLine = st2.nextToken();
                                strLine = strLine.trim();
                                //System.out.println("\n label and instruction " + strLine);

                                //System.out.println("\n strLine =" + strLine);
                                ScanFile.tokenizeInstruction(strLine);
                                if (frontend.FrontEnd.exceptionraised == 0)
                                {
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.GREEN);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,Emit.ins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Executed Successfully \n",Color.GREEN);
                                    
//                                    frontend.FrontEnd.statuswindow.append("INSTRUCTION EXECUTED SUCCESFULLY \n");
                                } else
                                {
                                  FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.RED);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,Emit.ins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Failed To Execute \n",Color.RED);  
//                                    frontend.FrontEnd.statuswindow.append("INSTRUCTION  FAILED TO EXECUTE \n");
                                }

                            }

                        }
                    }
                    if (!strLine.isEmpty())
                    {
                        ScanFile.tokenizeInstruction(strLine);
                        if (frontend.FrontEnd.exceptionraised == 0)
                        {
                            FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.GREEN);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,Emit.ins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Executed Successfully \n",Color.GREEN);
//                            frontend.FrontEnd.statuswindow.append("INSTRUCTION EXECUTED SUCCESFULLY \n");
                        } else
                        {
                             FrontEnd.appendToPane(FrontEnd.statuswindow,"Instruction ",Color.RED);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow,Emit.ins,Color.BLACK);
                                    FrontEnd.appendToPane(FrontEnd.statuswindow, " Failed To Execute \n",Color.RED); 
                        }
                    }


                    //initial_pos=end_pos+1;
                }
//                else
//                {
//                    callbbeq = false;
//                    frontend.FrontEnd.steprun.setEnabled(false);
//                    frontend.FrontEnd.warning.setVisible(true);
//                    frontend.FrontEnd.cross.setVisible(true);
//                    frontend.FrontEnd.cross.setEnabled(true);
//                    commentflag=false;
//                }

                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            }
        }
      
        catch (BadLocationException ex)
            {
               // ex.printStackTrace();
            }
        catch (IOException ex)
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Unable To Access Source File:" + fpath.getName() + " \n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Unable to access the source file:" + fpath.getName() + " \n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
            }
            
        
      
}


    @Override
    public void run()
      {
        //System.out.println("step run run()");
        scanStart();
      }
}
