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
import edu.syr.bytecast.amd64.api.constants.OperandTypeMemoryEffectiveAddress;
import edu.syr.bytecast.amd64.api.constants.RegisterType;
import edu.syr.bytecast.amd64.api.instruction.IInstruction;
import edu.syr.bytecast.amd64.api.instruction.IOperand;
import edu.syr.bytecast.amd64.impl.instruction.operand.OperandSectionName;
import edu.syr.bytecast.interp.amd64.AMD64Environment;
import edu.syr.bytecast.interp.amd64.IISAInstruction;
import java.util.List;


public class ISAInstructionCALLQ implements IISAInstruction {

  @Override
  public InstructionType getInstructionType() {
      return InstructionType.CALLQ;
  }

  @Override
  public long execute(AMD64Environment env, IInstruction instruction) {
      List<IOperand> operands = instruction.getOperands();
      long ret = 0;
      if(operands.size()==2)
      {
           IOperand op1 = operands.get(0);
           IOperand op2 = operands.get(1);
           
           OperandTypeMemoryEffectiveAddress op_addr =(OperandTypeMemoryEffectiveAddress)op1.getOperandValue();
           long val1= env.getMemoryAddress(op_addr);
           
           String section_name = "";
           if(op2 != null && op2.getOperandType() == OperandType.SECTION_NAME){
              section_name = (String)op2.getOperandValue();
           }
           
           //printf special case
           if(section_name.contains("printf")){
               //RDI contains a pointer to the input string
               long curr_str_addr = env.getValue(RegisterType.RDI);
               long arg1_val = env.getValue(RegisterType.ESI);
               
               String string_to_print = "";
               char curr_char = (char)env.getValue(curr_str_addr, 1);
               while(curr_char != '\0'){
                   string_to_print += curr_char;
                   curr_str_addr=curr_str_addr+1;
                   curr_char = (char)env.getValue(curr_str_addr, 1);
               }
               string_to_print = string_to_print.replace("%d", String.valueOf(arg1_val));
               System.out.println(string_to_print);
           } else {
               return val1;
           } 
      }
      return ret ;
  }

}
