/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import frontend.FrontEnd;
import java.awt.Color;

/**
 *
 * @author NIkhil
 */
public class Register
{

    public static Register_spec[] r;

    public Register()
      {
        r = new Register_spec[17];
        for (int i = 0; i < 17; i++)
        {
            r[i] = new Register_spec();
        }

      }

    public static void resetRegisters()
      {
        for (int i = 0; i < 14; i++)
        {
            r[i].b = 0;

        }
        r[16].b = 0;
        r[15].b = backend.ScanFile.endOfProgram;
        backend.Register.r[14].b = 5000;
      }

    public static int convertRegister(String reg)
      {
        if (reg.equals("RA"))
        {
            return 15;
        }
         else if (reg.equals("SP"))
        {
            return 14;
        }
        else if (reg.startsWith("R"))
        {
            //System.out.println("yesssssssssssssssssssssss");
            int no = 0;
            String num="";
             num= reg.substring(1);
            if(num.equals(""))
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Register Number Missing  "+ reg+"\n",Color.RED);
                frontend.FrontEnd.exceptionraised++;
                return -1;
                
                
            }
            try
            {
                no = Integer.valueOf(num);
            } 
            catch (Exception e)
            {
               // System.out.println("got in here ");
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Invalid Operand Register  "+ reg+"\n",Color.RED);
                frontend.FrontEnd.exceptionraised++;
                return -1;
                
                
                
                
            }
            if (no < 0 || no > 15)
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Register Out OF Range "+ reg  +"\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("\n ERROR in line " + backend.ScanFile.current_line + ":"+ " REGISTER  "+ reg +" NOT FOUND" +"\n" );
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
                return -1;
            }
            return no;
        } 
        else
        {   FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Unknown Operand Register " + reg+"\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("\n ERROR in line " + backend.ScanFile.current_line + ": UNKNOWN REGISTER " + reg+"\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return -1;
        }

      }
}
