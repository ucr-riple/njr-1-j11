/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import backend.ScanFile;
import backend.step_over;
import frontend.FrontEnd;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author ROCK
 */
public class CALL implements InsInterface2
{

    public static Long linkregister = new Long(0);

    /**
     *
     * @param ins
     */
    @Override
    public void execute(String ins, String token2)
      {
        //System.out.println("\n REACHED CALLLLLLL \n ");
        //System.out.println(token2);
         if(token2.startsWith("."))
        {
        execcall(token2);
         }
         else {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + "): Label "+ token2+" Must Start With ."+"\n",Color.RED);

            frontend.FrontEnd.exceptionraised++;
            return;
            
        }

      }

    private void execcall(String token2)
      {

        // long linkregister ;
       
        try
        {
            token2 = token2.substring(1);
            token2 = token2.trim();
            Long lno = null;
            try
            {
                lno = (Long) backend.FirstPass.branchtable.get(token2.toUpperCase());
                // cnvrt(lno);
                //System.out.print("\n calling " + token2 + "on line " + lno);

            } catch (Exception e)
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR in line " + backend.ScanFile.current_line + " Label " + token2 + " not found in the program.",Color.RED);
//                frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + " Label " + token2 + " not found in the program.");
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
            if (lno != null && lno >= 0)
            {
                linkregister = backend.ScanFile.br.getFilePointer();
                //System.out.println("lno " + lno);
                backend.ScanFile.br.seek(lno); //current position of file poinnter to lno
                //System.out.println("HI AM Here with return address" + linkregister.intValue());
                ScanFile.call_count += 1;
                try
                {
                    step_over.callEncounter += 1;
                } catch (Exception e)
                {
                }
                //System.out.println("call_count is: " + ScanFile.call_count);
                backend.StepRun.callbbeq = true;
                backend.Register.r[15].b = linkregister.intValue();
                backend.ScanFile.pos = lno;
            }

//            } else if (lno == -1)
//            {
//                //printf statement
//                linkregister = backend.ScanFile.br.getFilePointer();
//                backend.ScanFile.br.seek(backend.Register.r[0].b);
//                String tmp = backend.ScanFile.br.readLine();
//
//                String strline = backend.ScanFile.br.readLine();
//                strline = strline.trim();
//                System.err.println("branchd to " + strline);
//                if (strline.startsWith(".ascii"))
//                {
//                    strline = strline.substring(6);
//
//                    backend.ohandling.printhandle(strline);
//                }
//
//                backend.ScanFile.br.seek(linkregister);
//            } else if (lno == -3)
//            {
//                backend.Register.r[0].b = backend.Register.r[0].b % backend.Register.r[1].b;
//
//            }
        } catch (IOException ex)
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR in line " + backend.ScanFile.current_line + ": Unable to branch to that location.",Color.RED);
        
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + backend.ScanFile.current_line + ": Unable to branch to that location.");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return;
        }

     
      }
}
