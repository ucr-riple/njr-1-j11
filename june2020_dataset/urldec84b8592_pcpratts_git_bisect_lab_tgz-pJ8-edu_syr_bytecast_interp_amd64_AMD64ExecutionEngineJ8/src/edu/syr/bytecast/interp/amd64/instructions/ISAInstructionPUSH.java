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
import edu.syr.bytecast.amd64.api.constants.OperandType;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.interp.amd64.AMD64Environment;
import edu.syr.bytecast.interp.amd64.IISAInstruction;
import java.util.List;


public class ISAInstructionPUSH implements IISAInstruction {

    @Override
    public InstructionType getInstructionType() {
        return InstructionType.PUSH;
    }

    @Override
    public long execute(AMD64Environment env, IInstruction instruction) {
        List<IOperand> ops = instruction.getOperands();
        
        if(ops.size() == 1){
            IOperand op = ops.get(0);
                       
            int op_width = env.getOperandWidth(op);
            
            //Get the stack segment value
            long ss = env.getValue(RegisterType.SS);
            
            //Get the current stack pointer value;
            long sp = env.getValue(RegisterType.RSP);
            
            //Subtract the operand width from SP to set new SP
            sp = sp-op_width;
            
            //Assuming we use a 64 bit stack...
            env.setValue(RegisterType.RSP, sp);
            
            //Set the memory location contained in SS:RSP to the operand contents
            env.setValue(ss+sp, op, op_width);
        }
        
        return 0;
    }
}
