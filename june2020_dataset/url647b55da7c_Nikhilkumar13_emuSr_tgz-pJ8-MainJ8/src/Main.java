
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import backend.Emit;
import backend.Memory;
import backend.Register;
import frontend.FrontEnd;
import backend.NFlagRegister;


public class Main
{

    public static void main(String[] args)
      {
        
        //boolean commment=false;
          System.out.println("Simple Risc Emulator Started..");
        

        Register registerset = new Register();
        backend.Register.r[15].b = backend.ScanFile.endOfProgram;//retrun address
        //  Condition condition = new Condition();
        NFlagRegister f;
        f = new NFlagRegister();
        Memory memory = new Memory();
        Emit e = new Emit();
        e.createHash();
        FrontEnd fe = new FrontEnd();
        return ;
      
        
                
}
}
