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
public class calcop
{

    public static int calcvalue(String token)
      {
        int result = 0;
         if (token.equals("RA"))
        {
            return backend.Register.r[15].b;
        }
         else if (token.equals("SP"))
        {
            return backend.Register.r[14].b;
        }

        else if (token.startsWith("R"))
        {

            int no3 = backend.Register.convertRegister(token);
           
             if(no3==-1)
             {
                  FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ") Invalid Register Accesed Returning Zero\n",Color.RED);
                 return 0;
             }
             
                result = backend.Register.r[no3].b;
            
           

        } 
        else if (Myfunction.isInteger(token) == 0)
        {
             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ") Invalid Immediate "+token+" Decimal Or Hex Format Allowed \n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR(" + backend.ScanFile.current_line + ")" + token + " isnot a valid operand.");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;

        } 
        else
        {
            result = Myfunction.getimme((token));
        }

        return result;
      }
}
