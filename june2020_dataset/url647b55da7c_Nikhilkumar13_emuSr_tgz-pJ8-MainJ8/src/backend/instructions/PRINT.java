/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

/**
 *
 * @author Nikhil
 */
import backend.Register;
import backend.ScanFile;
import frontend.FrontEnd;
import java.awt.Color;

public class PRINT implements InsInterface2
{

    /**
     *
     * @param ins
     * @param token
     */
    @Override
    public void execute(String ins, String token)
      {
        int modified = Register.convertRegister(token);

        if(modified==-1)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR:(" + ScanFile.current_line + ") Failed To Execute Print Instruction \n",Color.RED);
           
//            frontend.FrontEnd.statuswindow.append("Printing Register.... R" + i + " = " + modified + "\n");
        } 
        else
        {
             backend.StepRun.callbbeq = false;

            //System.out.println("printing");
            
             FrontEnd.appendToPane(FrontEnd.statuswindow,"Printing Register...." + token + " = " + Register.r[modified].b + "\n",Color.BLUE);
        
             
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Register Not Fouund \n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
           

        }

      }
}
