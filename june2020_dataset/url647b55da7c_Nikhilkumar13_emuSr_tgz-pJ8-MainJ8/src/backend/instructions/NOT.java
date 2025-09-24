/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import frontend.FrontEnd;
import java.awt.Color;

/**
 *
 * @author NIKHIL
 */
public class NOT implements InsInterface3
{

    static Integer no, no2;
    static int operand_2;

    @Override
    public void execute(String ins, String token3, String token4)
      {

        execNOT(ins, token3, token4);
      }

    public static void execNOT(String ins, String token3, String token4)
      {
        backend.StepRun.callbbeq = false;

        no = backend.Register.convertRegister(token3);
        int modified = checkmod.modify(ins, token4);
        if (no > -1)
        {
            
            {

                try
                {
                    backend.Register.r[no].b = ~modified + 1;
                } catch (Exception e)
                {
                     FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): NOT operation Failed\n",Color.RED);
//                    frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + ":" + "NOT operation failed");
                    //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
                   // frontend.FrontEnd.exceptionraised++;
                    return;
                }

            }
        } else
        {
             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Invalid Destination Regiter For NOT Instruction " + token3 + "\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + ":" + "The destination operand of NOT should be a register and " + token3 + " isnot a valid register.");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            //frontend.FrontEnd.exceptionraised++;
            return;
        }

      }
}
