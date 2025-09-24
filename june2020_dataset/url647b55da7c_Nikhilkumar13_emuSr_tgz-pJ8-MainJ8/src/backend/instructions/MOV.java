/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import frontend.FrontEnd;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author Nikhil
 */
public class MOV implements InsInterface3
{

    
    static int no2, no3;

    @Override
    public void execute(String ins, String token3, String token4)
      {

     
          //System.out.println(token4);
     
        backend.StepRun.callbbeq = false;

        int no = backend.Register.convertRegister(token3);
        int modified = checkmod.modify(ins, token4);
        if (no > -1)
        {

            try
            {

                backend.Register.r[no].b = modified;
            } catch (Exception e)
            {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ")MOV Operation Failed",Color.RED);
//                frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + ": INVALID OPERAND");
                //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                frontend.FrontEnd.exceptionraised++;

            }

        } else if (no == -2)
        {
             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ")Invalid Destination Register" + token3 +"\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR(" + backend.ScanFile.current_line + ")Invalid Destination Register" + token3 + " isnot a valid operand.");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return;
        }
      }
}
