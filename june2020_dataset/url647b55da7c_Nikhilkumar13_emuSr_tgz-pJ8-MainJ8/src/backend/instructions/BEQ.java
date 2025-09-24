/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import backend.NFlagRegister;
import frontend.FrontEnd;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author ROCK
 */
public class BEQ implements InsInterface2
{

    @Override
    public void execute(String ins, String token2)
      {
        if(token2.startsWith("."))
        {
        if (NFlagRegister.E == 1)
        {
            execbeq(token2);
            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        } 
        }
       
            else {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Label "+ token2+" Must Start With ."+"\n",Color.RED);

            frontend.FrontEnd.exceptionraised++;
            return;
            
        
        }
            
       

      }

    private void execbeq(String token2) // B{cond} label
      {
        //System.out.print(":)) i came here in BEQ :" + token2);
       // long linkregister;
        
        try
        {
            token2 = token2.trim().substring(1);
            Long lno = null;
            try
            {
                lno = (Long) backend.FirstPass.branchtable.get(token2.toUpperCase());
                //   cnvrt(lno);
                //System.out.print(lno);
            } catch (Exception e)
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR:" + backend.ScanFile.current_line + " Label " + token2 + " Not Found In The Program\n",Color.RED);
//                frontend.FrontEnd.statuswindow.append("ERROR: " + backend.ScanFile.current_line + " Label " + token2 + " not found in the program.");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;
                return;
            }
             if(lno==null)
            {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR:" + backend.ScanFile.current_line + " Label " + token2 + " Not Found In The Program."+"\n",Color.RED);

                frontend.FrontEnd.exceptionraised++;
                return;
                
            }
            if (lno >= 0)
            {
                backend.ScanFile.br.seek(lno);
                backend.ScanFile.pos = lno;
                backend.StepRun.callbbeq = true;
                // //System.out.println("TESTING\n");
            }
//          
        } catch (IOException ex)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Failed To Execute BEQ Instruction "+token2+"\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + ": Unable To Branch To Label "+token2+"\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return;
        }

      }
    
     
    
}
