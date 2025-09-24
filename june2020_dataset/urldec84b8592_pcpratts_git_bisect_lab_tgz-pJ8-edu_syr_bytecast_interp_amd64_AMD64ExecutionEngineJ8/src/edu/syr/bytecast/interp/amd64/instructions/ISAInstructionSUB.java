/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.interp.amd64.instructions;

import edu.syr.bytecast.amd64.api.constants.InstructionType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.interp.amd64.AMD64Environment;
import edu.syr.bytecast.interp.amd64.IISAInstruction;
import java.util.List;


public class ISAInstructionSUB implements IISAInstruction {
   
    @Override
    public InstructionType getInstructionType() {
        return InstructionType.SUB;
    }

    @Override
    public long execute(AMD64Environment env, IInstruction instruction) {
       List<IOperand> operands = instruction.getOperands();
       if(operands.size() == 2)
       {
           IOperand op1 = operands.get(0);
           IOperand op2 = operands.get(1);
           
           int op_width1 = env.getOperandWidth(op1);
           int op_width2 = env.getOperandWidth(op1);
           
           long val1= env.getValue(op1, op_width1);
           long val2= env.getValue(op2, op_width2);
           
           long diff = val1 - val2;

           env.setValue(op1, diff, op_width1);

          long returned = env.getValue(op1, op_width1);   

           if(checkOverflow(val1,val2,returned)){
               env.setValue(RegisterType.OF, 1);
           }
           else {
               env.setValue(RegisterType.OF, 0);           
           }
           
           if(checkCarry(val1,val2,returned)){
               env.setValue(RegisterType.CF, 1);
           }
           else {
               env.setValue(RegisterType.CF, 0);              
           }    
           
           if(returned == 0){
               env.setValue(RegisterType.ZF, 1);
           } else {
               env.setValue(RegisterType.ZF, 0);
           }
           
           if(returned < 0){
              env.setValue(RegisterType.SF, 1);
           } else {
              env.setValue(RegisterType.SF, 0); 
           }
           
           if(ISAUtil.isEvenParity(returned)){
               env.setValue(RegisterType.PF, 1);
           } else {
               env.setValue(RegisterType.PF, 0);
           }
       }
       return 0;
    }
    
    private static boolean checkOverflow(long a, long b, long result)
    {
        if(a < 0 && b > 0 && result > 0) {
            return true;
        }
        else if(a > 0 && b < 0 && result < 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private static boolean checkCarry(long a, long b, long result){
        if(a > 0 && b < 0) {
            return true;
        }
        else if(a < 0 && b < 0 && result < 0) {
            return true;
        }
        else {
            return false;  
        }
    }
}

