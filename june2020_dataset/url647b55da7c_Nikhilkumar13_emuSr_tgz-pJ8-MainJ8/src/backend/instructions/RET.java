/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import backend.ScanFile;
import frontend.FrontEnd;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author ROCK
 */
public class RET implements InsInterface1
{

    /**
     *
     * @param token1
     */
    @Override
    public void execute(String ins)
      {
        backend.StepRun.callbbeq = false;
        //System.out.println("ret encountered");
        try
        {
            backend.ScanFile.br.seek(backend.Register.r[15].b);
            backend.ScanFile.pos = backend.Register.r[15].b.longValue();
            ScanFile.call_count -= 1;
        } catch (IOException ex)
        {
            //Logger.getLogger(RET.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("returning me problem boss");
            frontend.FrontEnd.exceptionraised++;
             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR:(" +  backend.ScanFile.current_line + ")  Invalid Return Address Stored In R15\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR:(" +  backend.ScanFile.current_line + ")  Invalid return address\n");
            return;
        }
        
        //System.out.println("call_count is: " + ScanFile.call_count);

      }
}
