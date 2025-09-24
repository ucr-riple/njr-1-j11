/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import backend.ScanFile;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import backend.instructions.Myfunction.*;
import frontend.FrontEnd;
import frontend.handlers;
import java.awt.Color;

/**
 *
 * @author Nikhil
 */
public class LD implements InsInterface3
{
   

    @Override
    public void execute(String ins, String Rd, String addressing_mode)
      {
   
        
       
       int reg_id =0;
       
        
 
       
         backend.StepRun.callbbeq = false;
        int address = backend.instructions.Myfunction.evaluateAddress(addressing_mode);
        
         reg_id = backend.Register.convertRegister(Rd);
        if (reg_id> -1)
        {

           if(address>-1)
           {
            
             backend.Register.r[reg_id].b =  backend.Memory.get(address);
             (new handlers()).update(handlers.regMode);
                
                
            
          }
          
           else
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ") Failed To Execute LOAD Instruction  \n",Color.RED);
               
           }
        }
          
        else 
        {

             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + backend.ScanFile.current_line + ") Failed To Execute LOAD Instruction \n",Color.RED);
        }
       // System.out.println(reg_id*address);

      }


      }

