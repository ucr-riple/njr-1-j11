/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.instructions;

import backend.ScanFile;
import frontend.FrontEnd;
import java.awt.Color;

/**
 *
 * @author Nikhil
 */
public class Myfunction
{

    public static int isInteger(String s)
      {
        try
        {
            Integer.parseInt(s);
        } catch (NumberFormatException e)
        {
            if (s.substring(0, 2).equals("0X"))
            {

                return 2;
            }
            //System.out.println("  loving it enjoyed");
            return 0;
        }
        // only got here if we didn't return false
        return 1;
      }

    public static int getimme(String token)
      { 
        
        int a = isInteger(token);
          //
          if (a == 1)
        {
           // System.out.println(token);
            return Integer.parseInt(token,10);
        } 
        else
        {
            
           // System.out.println(Integer.decode());
            try{ 
            return Integer.parseInt(token.substring(2), 16);
         }
        
         
        catch(Exception e)
                    {
                         FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + ") Invalid Hex Immediate  0x"+ token.substring(2) +" \n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Address should start with [\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
                       
                        
                    }
        }
            
        
        return 0;

      }

    public static Integer evaluateAddress(String addressing_mode)
      {   //System.out.println("\neavluting addressssss");

        //System.out.println("address 1:"+addressing_mode+":)");
          //System.out.println(addressing_mode);
          addressing_mode=  addressing_mode.replaceAll("SP", "R14");
          addressing_mode= addressing_mode.replaceAll("RA", "R15");
        
        Integer address = 0;
        addressing_mode = addressing_mode.trim();
          //System.out.println("snfknasj");
       if (addressing_mode.contains("[")&addressing_mode.contains("]")&addressing_mode.contains("R"))
        
        
        {
           // System.out.println("nsflkasn");
            String st = addressing_mode;
            int l=0;
            int l2=0;
            String tmp2="";
            String tmp="";

            int r = addressing_mode.indexOf("R");
            tmp = st.substring(0, r - 1);
            if(!tmp.equals(""))
            { 
                l=checkmod.modify("st", tmp);
                //System.out.println("hiiiiiii");
            }
            else l=0;
                
                
            
           
            
  
            
            tmp2 = st.substring(r, st.length() - 1);
            
            if(!tmp2.substring(1).equals(""))
            {  
                
               
                l2=backend.Register.convertRegister(tmp2);
            
          
                
            }
            else
            {
                FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + ") Missing Register Number After R \n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Address should start with [\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return -1;
                
            }
            if(l2==-1)
            {
                 FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + ") Invalid Register Accesed In Store Operation \n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Address should start with [\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return -1;
                
            }
             address = backend.Register.r[l2].b + l;
           // System.out.println("address ->>"+address);
            return address;
            
               
            
            
            
             

            
            

        } 
        
        else if(addressing_mode.isEmpty())
        {
            FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + ") Missing Source Operand For STORE Instruction\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Address should start with [\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return -1;
            
        }
        
        else
        {
             FrontEnd.appendToPane(FrontEnd.statuswindow,"ERROR(" + ScanFile.current_line + ") Invalid Source Operand Format Missing [/]\n",Color.RED);
//            frontend.FrontEnd.statuswindow.append("ERROR in line " + ScanFile.current_line + ": Address should start with [\n");
            //frontend.FrontEnd.statuswindow.setCaretPosition(frontend.FrontEnd.statuswindow.getText().length());
            frontend.FrontEnd.exceptionraised++;
            return -1;

        }

      

      }
}
