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
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.interp.amd64.AMD64Environment;
import edu.syr.bytecast.interp.amd64.IISAInstruction;
import java.util.List;

/**
 * 
 * @author sheng
 */
public class ISAInstructionMOV implements IISAInstruction {

    @Override
    public InstructionType getInstructionType() {
        return InstructionType.MOV;
    }

    @Override
    public long execute(AMD64Environment env, IInstruction instruction) {
        List<IOperand> operands = instruction.getOperands();
        if (operands == null || operands.size() != 2) {
            return 0;
        }
        
        // Move the value from the second operand to the first one.
        IOperand second=operands.get(1);
        IOperand first=operands.get(0);
        int op_width1 = env.getOperandWidth(first);
        int op_width2 = env.getOperandWidth(second);
        int write_width = op_width1;
        if(first.getOperandType() == OperandType.MEMORY_EFFECITVE_ADDRESS){
            write_width = op_width2;
        }
        long value=env.getValue(second, write_width);
        env.setValue(first, value, write_width);
        return 0;
    }
}
