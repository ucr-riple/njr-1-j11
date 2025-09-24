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
import edu.syr.bytecast.interp.amd64.Register;
import java.util.List;


public class ISAInstructionSHR implements IISAInstruction {

  @Override
  public InstructionType getInstructionType() {
      return InstructionType.SHR;
  }

  @Override
  public long execute(AMD64Environment env, IInstruction instruction) {
         List<IOperand> operands = instruction.getOperands();
        if (operands.size() > 0) {


            long shift_amount = 1;
            if (operands.size() == 2) {
                IOperand op2 = operands.get(1);
                int op_width2 = env.getOperandWidth(op2);
                shift_amount = env.getValue(op2, op_width2);
            }

            if (shift_amount != 0) {
                IOperand op1 = operands.get(0);

                int op_width1 = env.getOperandWidth(op1);

                long val1 = env.getValue(op1, op_width1);
                
                //Set carry flag to last bit out
                env.setValue(RegisterType.CF, val1 & 0x1);
                
                val1 = val1 >>> shift_amount;
                
                if(val1 == 0){
                    env.setValue(RegisterType.ZF, 1);
                } else {
                    env.setValue(RegisterType.ZF, 0);
                }
                
                env.setValue(RegisterType.SF, 0);

                if(ISAUtil.isEvenParity(val1)) {
                    env.setValue(RegisterType.PF, 1);                    
                } else {
                    env.setValue(RegisterType.PF, 0);               
                }
                
                env.setValue(op1, val1, op_width1);

            }
        }
        return 0;
    }
}
