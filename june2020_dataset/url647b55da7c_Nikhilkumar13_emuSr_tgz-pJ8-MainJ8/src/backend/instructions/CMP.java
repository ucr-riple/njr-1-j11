/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import backend.NFlagRegister;
import frontend.FrontEnd;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikhil
 */
public class CMP implements InsInterface3
{

    static int[] binaryconvert = null;
    static int no2, no3, chck;
    static int b = 0;
    static int operand_2;

    @Override
    public void execute(String ins, String token3, String token4)
      {
        execCMP(ins, token3, token4);

      }

    public static void execCMP(String ins, String token3, String token4)
      {

        backend.StepRun.callbbeq = false;
        // String num;
        no2 = backend.Register.convertRegister(token3);
        int modified = checkmod.modify(ins, token4);
        if (no2 > -1)
        {
            

            if (backend.Register.r[no2].b == modified)
            {
                NFlagRegister.E = 1;

            } else
            {
                NFlagRegister.E = 0;
            }
            if (backend.Register.r[no2].b > modified)
            {
                NFlagRegister.GT = 1;
            } else
            {
                NFlagRegister.GT = 0;
            }

        } else
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR in line " + backend.ScanFile.current_line + ":" + "The operands of CMP should be a register and " + token3 + " isnot a valid register.",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + ":" + "The operands of CMP should be a register and " + token3 + " isnot a valid register.");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            // //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        }

      }
}
