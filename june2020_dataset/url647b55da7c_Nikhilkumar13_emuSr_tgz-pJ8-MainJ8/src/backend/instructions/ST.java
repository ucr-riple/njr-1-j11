/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import frontend.FrontEnd;
import frontend.handlers;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikhil
 */
public class ST implements InsInterface3
{

  

    @Override
    public void execute(String ins, String Rd, String addressing_mode)
      {
        backend.StepRun.callbbeq = false;
        int address = backend.instructions.Myfunction.evaluateAddress(addressing_mode);
        
        int reg_id = backend.Register.convertRegister(Rd);
        if (reg_id> -1)
        {

           if(address>-1)
           {
            Integer data = 0;
               data = backend.Register.r[reg_id].b;
            
                backend.Memory.put(data, address);
                // System.err.println("store executed ::"+backend.Memory.getsize());
                handlers.update_memorytable(backend.Memory.getsize(), handlers.regMode);
            
        }
          
           else
           {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ") Failed To Execute STORE Instruction \n",Color.RED);
               
           }
        }
          
        else 
        {

             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ") Failed To Execute STORE Instruction \n",Color.RED);
        }
       // System.out.println(reg_id*address);

      }
}
