package backend;

import backend.InstructionSet.*;
import backend.instructions.InsInterface2;
import backend.instructions.InsInterface1;
import backend.instructions.InsInterface3;
import backend.instructions.InsInterface4;
import frontend.FrontEnd;
import java.awt.Color;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author nikhil
 */
public class Emit
{

    public static Hashtable ht;
    public static String ins;

    public Emit()
      {
        ht = new Hashtable();
      }

    public void createHash()
      {
        ht.clear();

        ADD[] add = backend.InstructionSet.ADD.values();
        for (int i = 0; i < add.length; i++)
        {
            ht.put(add[i].name(), new backend.instructions.ADD());

        }
        AND[] and = backend.InstructionSet.AND.values();
        for (int i = 0; i < and.length; i++)
        {
            ht.put(and[i].name(), new backend.instructions.AND());

        }

        CALL[] call = backend.InstructionSet.CALL.values();
        for (int i = 0; i < call.length; i++)
        {
            ht.put(call[i].name(), new backend.instructions.CALL());

        }

        CMP[] cmp = backend.InstructionSet.CMP.values();
        for (int i = 0; i < cmp.length; i++)
        {
            ht.put(cmp[i].name(), new backend.instructions.CMP());

        }
        LSR[] lsr = backend.InstructionSet.LSR.values();
        for (int i = 0; i < lsr.length; i++)
        {
            ht.put(lsr[i].name(), new backend.instructions.LSR());

        }
        MOV[] mov = backend.InstructionSet.MOV.values();
        for (int i = 0; i < mov.length; i++)
        {
            ht.put(mov[i].name(), new backend.instructions.MOV());

        }
        DIV[] div = backend.InstructionSet.DIV.values();
        for (int i = 0; i < div.length; i++)
        {
            ht.put(div[i].name(), new backend.instructions.DIV());

        }

        LSL[] lsl = backend.InstructionSet.LSL.values();
        for (int i = 0; i < lsl.length; i++)
        {
            ht.put(lsl[i].name(), new backend.instructions.LSL());

        }

        ASR[] asr = backend.InstructionSet.ASR.values();
        for (int i = 0; i < asr.length; i++)
        {
            ht.put(asr[i].name(), new backend.instructions.ASR());

        }
        NOT[] not = backend.InstructionSet.NOT.values();
        for (int i = 0; i < not.length; i++)
        {
            ht.put(not[i].name(), new backend.instructions.NOT());

        }

        SUB[] sub = backend.InstructionSet.SUB.values();
        for (int i = 0; i < sub.length; i++)
        {
            ht.put(sub[i].name(), new backend.instructions.SUB());

        }

        B[] b = backend.InstructionSet.B.values();
        for (int i = 0; i < b.length; i++)
        {
            ht.put(b[i].name(), new backend.instructions.B());

        }

        BEQ[] beq = backend.InstructionSet.BEQ.values();
        for (int i = 0; i < beq.length; i++)
        {
            ht.put(beq[i].name(), new backend.instructions.BEQ());

        }
        BGT[] bgt = backend.InstructionSet.BGT.values();
        for (int i = 0; i < beq.length; i++)
        {
            ht.put(bgt[i].name(), new backend.instructions.BGT());

        }

        LD[] ld = backend.InstructionSet.LD.values();
        for (int i = 0; i < ld.length; i++)
        {
            ht.put(ld[i].name(), new backend.instructions.LD());

        }

        ST[] st = backend.InstructionSet.ST.values();
        for (int i = 0; i < st.length; i++)
        {
            ht.put(st[i].name(), new backend.instructions.ST());

        }
        OR[] or = backend.InstructionSet.OR.values();
        for (int i = 0; i < or.length; i++)
        {
            ht.put(or[i].name(), new backend.instructions.OR());

        }

        MOD[] mod = backend.InstructionSet.MOD.values();
        for (int i = 0; i < mod.length; i++)
        {
            ht.put(mod[i].name(), new backend.instructions.MOD());

        }

        MUL[] mul = backend.InstructionSet.MUL.values();
        for (int i = 0; i < mul.length; i++)
        {
            ht.put(mul[i].name(), new backend.instructions.MUL());

        }
        NOP[] nop = backend.InstructionSet.NOP.values();
        ht.put(nop[0].name(), new backend.instructions.NOP());
        RET[] ret = backend.InstructionSet.RET.values();
        ht.put(ret[0].name(), new backend.instructions.RET());
        PRINT[] print = backend.InstructionSet.PRINT.values();
        for (int i = 0; i < print.length; i++)
        {
            ht.put(print[i].name(), new backend.instructions.PRINT());
        }
      }

    public static void execute(StringTokenizer st)
      {

        // //System.out.println("/n hi bro "+ st.toString() + " /n");
         ins = null;
        try
        {
            ins = st.nextToken();
            if(ins.substring(1).equals("PRINT"))
            {
            ins = ins.replace(".", "");
            }
            
            //System.out.println("Instruction--> " + ins + " at line " + String.valueOf(backend.ScanFile.current_line));
        } catch (Exception e)
        {
            
            JOptionPane.showMessageDialog(null, "Some problem occured while execution. Check syntax or try re-starting emulator");

        }
          //System.out.println("emit me "+st.toString());
        int ct = ct(ins);

        String token1_1 = null;
        String token2_2 = null;
        String token2_3 = null;
        String token3_3 = null;
        String token2 = null;
        String token3 = null;
        String token4 = null;

        switch (ct)
        {

            case 1:
                InsInterface1 obj1 = (InsInterface1) ht.get(ins);

                try
                {
                    st.nextToken();
                     FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Operand Not required in "+ins+"\n",Color.RED);
                   // frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Operand Not required in "+ins+"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    obj1.execute(ins);

                } catch (Exception e)
                {
                     obj1.execute(ins);
                   

                    // //System.out.println("\n hello baby \n");
                }
                // ELSE IT CAN BE A LABEL'

                break;

            
            case 2:
                InsInterface2 obj2 = (InsInterface2) ht.get(ins);
                token2_2 = "";
                try
                {
                    
                    
                    
                    while (st.hasMoreElements())
                    {
                        token2_2 = token2_2 + st.nextToken();
                    }
                        
                    
                    token2_2 = token2_2.trim();
                    if(token2_2.equals(""))
                    {
                        FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Source Operand Mising for Instruction "+ins+"\n",Color.RED);

                     //frontend.FrontEnd.statuswindow.append("ERROR(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins+"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                    }
                    obj2.execute(ins, token2_2);
                } catch (Exception e)
                {
                   // e.printStackTrace();
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Operand Missing for Instruction "+ins+"\n",Color.RED);
                   // frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins+"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;

                }
                break;
            case 3:
                  InsInterface3 obj3 = (InsInterface3) ht.get(ins);

                try
                {
               
                    token2_3 = st.nextToken();
                }
               catch (Exception e)
                {
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Destination and Source Register Missing for Instruction "+ins+"\n",Color.RED);

                     //frontend.FrontEnd.statuswindow.append("ERROR(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins+"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                }
                try
                {
                   
                    token3_3 = "";
                    while (st.hasMoreElements())
                    {
                        // //System.out.println("\n In while loop");

                        token3_3 = token3_3 + st.nextToken();
                        // //System.out.println("token3_3 "+token3_3);
                    }
                    token3_3 = token3_3.trim();
                    if(token3_3.equals(""))
                    {
                        FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Source Operand Mising for Instruction "+ins+"\n",Color.RED);

                     //frontend.FrontEnd.statuswindow.append("ERROR(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins+"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                    }
                    ////System.out.println("\n token3_3"+token3_3);
                    obj3.execute(ins, token2_3, token3_3);
                } 
                catch (Exception e)
                {
                    FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Source Operand Mising for Instruction "+ins+"\n",Color.RED);

                     //frontend.FrontEnd.statuswindow.append("ERROR(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins+"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                }
                break;
            case 4:
                InsInterface4 obj4 = (InsInterface4) ht.get(ins);
                
                try
                {
                    token2 = st.nextToken();
                }
                 catch (Exception e)
                {
                       FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Destination and Source Register Missing for Instruction " +ins+"\n",Color.RED);
//                    frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins +"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                }
                try
                {
                token3 = st.nextToken();
                }
                 catch (Exception e)
                {
                       FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Source Registers Missing for Instruction " +ins+"\n",Color.RED);
//                    frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins +"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                }

                try
                {
                    
                    
                    
                    token4 = "";
                    while (st.hasMoreTokens())
                    {
                        token4 = token4 + st.nextToken();
                        //System.out.println("nikhil");

                    }
                    if(token4.equals(""))
                    {
                        FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): 2nd Source Operand Missing for Instruction " +ins+"\n",Color.RED);
//                    frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins +"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                        
                    }
                }
                catch (Exception e)
                { 
                    
                    
                   // System.out.println("hahahahahahh");
                       FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): 2nd Source Operand Missing for Instruction " +ins+"\n",Color.RED);
//                    frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Operand Missing for Instruction "+ins +"\n");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                    frontend.FrontEnd.exceptionraised++;
                    return;
                }
                    token4 = token4.trim();
                    
                  // System.out.println(token4);
                    obj4.execute(ins, token2, token3, token4);
                    

                 
                
                break;
            default:
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + "): Invalid Instruction " +ins+"\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("ERROR:(" + ScanFile.current_line + ")  Unknown Command " +ins+"\n");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
                return;

        }

      }

    public static int ct(String ins)
      {
        int ct = 5;
        if (ht.get(ins) instanceof InsInterface1)
        {
            ct = 1;

        } else if (ht.get(ins) instanceof InsInterface2)
        {
            ct = 2;
        } else if (ht.get(ins) instanceof InsInterface3)
        {
            ct = 3;
            //System.out.println("\n #####################");

        } else if (ht.get(ins) instanceof InsInterface4)
        {
            ct = 4;
        }

        return ct;
      }
}
