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
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.interp.amd64.AMD64Environment;
import edu.syr.bytecast.interp.amd64.IISAInstruction;
import java.util.List;

public class ISAInstructionJGE implements IISAInstruction {

  @Override
  public InstructionType getInstructionType() {
    return InstructionType.JGE;
  }

  @Override
  public long execute(AMD64Environment env, IInstruction instruction) {
    List<IOperand> operands = instruction.getOperands();
    
    if(operands == null || operands.size() != 1) {
      return 0;
    }
    
    IOperand op = operands.get(0);
    long addr = env.getMemoryAddress((OperandTypeMemoryEffectiveAddress)op.getOperandValue());
    
    long sf = env.getValue(RegisterType.SF);
    long of = env.getValue(RegisterType.OF);
    if (sf == of) {
      return addr;
    }
    return 0;
  }
}
